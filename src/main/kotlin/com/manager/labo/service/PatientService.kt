package com.manager.labo.service

import com.manager.labo.entities.Patient
import com.manager.labo.mapper.PatientMapper
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.PatientModel
import com.manager.labo.repository.PatientRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author pszerszen
 */
@Service
@Transactional
class PatientService(
    private val patientRepository: PatientRepository,
    private val patientMapper: PatientMapper
) {

    fun save(patient: Patient): Patient = patientRepository.save(patient)

    fun get(id: Long): Patient? = patientRepository.findByIdOrNull(id)

    fun getByPesel(pesel: String): Patient? = patientRepository.getByPesel(pesel)

    fun getPatientModelByPesel(pesel: String): PatientModel? = patientMapper.toPatientModel(getByPesel(pesel))

    fun getById(id: Long): PatientModel = patientMapper.toPatientModel(get(id))!!

    val all: List<PatientModel> get() = patientRepository.findAll().mapNotNull { patientMapper.toPatientModel(it) }

    fun fromExaminationRequest(model: ExaminationRequestModel): Patient {
        var patient: Patient? = patientRepository.getByPesel(model.pesel!!)
        patient = if (patient == null) patientMapper.fromExaminationRequestModel(model)
        else patientMapper.updateFromExaminationRequestMapper(patient, model)
        patient = patientRepository.save(patient)
        return patient
    }
}
