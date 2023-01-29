package com.manager.labo.service

import com.manager.labo.entities.Examination
import com.manager.labo.entities.ExaminationDetails
import com.manager.labo.entities.Patient
import com.manager.labo.mapper.ExaminationDetailsMapper
import com.manager.labo.mapper.ExaminationMapper
import com.manager.labo.model.ExaminationModel
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import com.manager.labo.repository.ExaminationDetailsRepository
import com.manager.labo.repository.ExaminationRepository
import com.manager.labo.repository.PatientRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer

/**
 * @author pszerszen
 */
@Transactional
@Service
class ExaminationService(
    private val examinationRepository: ExaminationRepository,
    private val examinationDetailsRepository: ExaminationDetailsRepository,
    private val patientRepository: PatientRepository,
    private val patientService: PatientService,
    private val examinationDetailsMapper: ExaminationDetailsMapper,
    private val examinationMapper: ExaminationMapper,
    private val icdService: IcdService
) {

    val all: List<ExaminationModel>
        get() = examinationRepository.findAll()
            .map { examinationMapper.toExaminationModel(it) }

    fun getExaminationRequestModel(examinationRequestId: Long): ExaminationRequestModel {
        val examination = examinationRepository.getReferenceById(examinationRequestId)

        return examinationMapper.toExaminationRequestModel(examination) {
            examination.examinationDetails
                .map { examinationDetailsMapper.toExaminationSummaryModel(it) { code2 -> icdService.getByCode2(code2).name2 } }
                .toMutableList()
        }
    }

    fun create(model: ExaminationRequestModel) {
        val patient: Patient = patientService.fromExaminationRequest(model)
        val examination: Examination = getExamination(model, patient)
        model.examinations
            .map { mapExaminationDetails(it, examination, isNewExamination(model)) }
            .forEach { examinationDetailsRepository.save(it) }
    }

    private fun getExamination(model: ExaminationRequestModel, patient: Patient): Examination {
        val examination: Examination = if (isNewExamination(model))
            examinationMapper.fromExaminationRequestModel(model) { patient }
        else examinationMapper.updateFromExaminationRequestModel(examinationRepository.findByIdOrNull(model.examinationId)!!, model)
        return examinationRepository.save(examination)
    }

    private fun mapExaminationDetails(model: ExaminationSummaryModel, examination: Examination, newExamination: Boolean): ExaminationDetails {
        return if (newExamination)
            examinationDetailsMapper.fromExaminationSummaryModel(model) { examination }
        else examinationDetailsMapper.updateFromExaminationSummaryModel(
            examination.examinationDetails.first { d: ExaminationDetails -> d.code == model.code },
            model
        )
    }

    fun update(examinationRequestModel: ExaminationRequestModel) {
        examinationRequestModel.examinations.forEach(Consumer { examinationDetailsModel: ExaminationSummaryModel ->
            val examinationDetails: ExaminationDetails = examinationDetailsRepository.getReferenceById(examinationDetailsModel.id!!)
                .copy(staffName = examinationDetailsModel.staffName, value = examinationDetailsModel.value)
            examinationDetailsRepository.save(examinationDetails)
        })
    }

    private fun isNewExamination(model: ExaminationRequestModel) = model.examinationId == null

    companion object {
        private val log = LoggerFactory.getLogger(ExaminationService::class.java)
    }
}
