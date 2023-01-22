package com.manager.labo.service

import com.manager.labo.entities.Examination
import com.manager.labo.entities.ExaminationDetails
import com.manager.labo.entities.Patient
import com.manager.labo.model.ExaminationModel
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import com.manager.labo.repository.ExaminationDetailsRepository
import com.manager.labo.repository.ExaminationRepository
import com.manager.labo.repository.PatientRepository
import com.manager.labo.utils.DateUtils
import com.manager.labo.utils.ValidDate
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.reflect.Field
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import java.util.function.Consumer
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

/**
 * @author pszerszen
 */
@Transactional
@Service
class ExaminationService(
    val examinationRepository: ExaminationRepository,
    val examinationDetailsRepository: ExaminationDetailsRepository,
    val patientRepository: PatientRepository,
    val icdService: IcdService
) {

    val all: List<ExaminationModel>
        get() = examinationRepository.findAll()
            .map { mapToExaminationModel(it) }

    fun getExaminationRequestModel(examinationRequestId: Long?): ExaminationRequestModel {
        return map(examinationRepository.findByIdOrNull(examinationRequestId))
    }

    fun create(examinationRequestModel: ExaminationRequestModel?) {
        val examination: Examination = map(examinationRequestModel)
        var patient: Patient = examination.patient
        if (patient.id == null) {
            patientRepository.save<Patient>(patient)
        } else {
            patient = patientRepository.merge(patient)
        }
        examination.patient = patient
        val examinationDetailses: Set<ExaminationDetails> = examination.getExaminationDetailses()
        examination.setExaminationDetailses(HashSet<E>())
        examinationRepository.save<Examination>(examination)
        examinationDetailses.forEach(Consumer { examiantionDetails: ExaminationDetails? ->
            examinationDetailsRepository.save<ExaminationDetails>(
                examiantionDetails
            )
        })
    }

    fun update(examinationRequestModel: ExaminationRequestModel) {
        examinationRequestModel.examinations.forEach(Consumer { examinationDetailsModel: ExaminationSummaryModel ->
            val examinationDetails: ExaminationDetails = examinationDetailsRepository.get(examinationDetailsModel.id)
            examinationDetails.staffName = examinationDetailsModel.staffName
            examinationDetails.value = examinationDetailsModel.value
            examinationDetailsRepository.merge(examinationDetails)
        })
    }

    fun getById(id: Long?): ExaminationModel {
        return mapToExaminationModel(examinationRepository.get(id))
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    fun validate(model: ExaminationRequestModel, validateExamiantions: Boolean): Set<String> {
        val errors: MutableSet<String> = Sets.newHashSet()
        for (field in model.javaClass.getDeclaredFields()) {
            extractAndValidateField(field, model, errors)
        }
        val examinations: List<ExaminationSummaryModel?> = model.examinations
        if (CollectionUtils.isEmpty(examinations)) {
            errors.add("Należy wybrać przynajmniej jedno badanie.")
        }
        if (validateExamiantions) {
            examinations.forEach(Consumer<ExaminationSummaryModel> { examination: ExaminationSummaryModel ->
                for (field in examination.javaClass.getDeclaredFields()) {
                    try {
                        extractAndValidateField(field, examination, errors)
                    } catch (e: Exception) {
                        log.error("Error while validating.", e)
                    }
                }
            })
        }
        return errors
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    private fun extractAndValidateField(field: Field, `object`: Any, errors: MutableSet<String>) {
        field.isAccessible = true
        val objectVal = field[`object`]
        val fieldValue = objectVal?.toString() ?: ""
        val error = validateField(field, fieldValue)
        if (error != null) {
            errors.add(error)
        }
    }

    private fun validateField(field: Field, fieldValue: String): String? {
        val notNullAnno: NotNull? = field.getAnnotation(NotNull::class.java)
        if (notNullAnno != null && StringUtils.isBlank(fieldValue)) {
            return notNullAnno.message
        }
        val patternAnno: Pattern? = field.getAnnotation(Pattern::class.java)
        if (patternAnno != null && !fieldValue.matches(patternAnno.regexp.toRegex())) {
            return patternAnno.message
        }
        val validDateAnno: ValidDate? = field.getAnnotation(ValidDate::class.java)
        if (validDateAnno != null) {
            try {
                LocalDate.parse(fieldValue, DateTimeFormatter.ofPattern(validDateAnno.dateFormat))
            } catch (e: DateTimeParseException) {
                return validDateAnno.message
            }
        }
        return null
    }

    private fun map(model: ExaminationRequestModel): Examination {
        val id: Long? = model.examinationId
        val newExamination = id == null
        val examination: Examination = if (id == null) Examination() else examinationRepository.findByIdOrNull(id)!!
        var patient: Patient? = patientRepository.getByPesel(model.pesel!!)
        if (patient == null) {
            patient = Patient()
        }
        patient.firstName = model.firstName
        patient.lastName = model.lastName
        patient.pesel = model.pesel
        patient.birth = DateUtils.toDate(model.birthDay!!)
        patient.address1 = model.address1
        patient.address2 = when (val it = model.address2) {
            "" -> null
            else -> it
        }
        patient.zipCode = model.zipCode
        patient.city = model.city
        patient.phone = model.phone
        examination.patient = patient
        val examinationDetailses: MutableSet<ExaminationDetails> = examination.examinationDetails
        val examinations: List<ExaminationSummaryModel> = model.examinations

        // creating new examination request
        if (newExamination) {
            examination.date = LocalDateTime.now()
            examination.code = examinations[0].code?.substring(0, 1)
        }
        examinations.forEach(Consumer {
            if (newExamination) {
                val detail = ExaminationDetails()
                detail.code = it.code
                detail.date = LocalDateTime.now()
                detail.examination = examination
                examinationDetailses.add(detail)
            } else {
                val detail = examinationDetailses
                    .stream()
                    .filter { d: ExaminationDetails -> d.code == it.code }
                    .findFirst()
                    .get()
                detail.staffName = it.staffName
                detail.value = it.value
            }
        })
        return examination
    }

    private fun map(examination: Examination): ExaminationRequestModel = ExaminationRequestModel(
        examination.id,
        examination.patient.firstName,
        examination.patient.lastName,
        examination.patient.pesel,
        DateUtils.fromDate(examination.patient.birth),
        examination.patient.address1,
        examination.patient.address2,
        examination.patient.zipCode,
        examination.patient.city,
        examination.patient.phone,
        examination.examinationDetails
            .map {
                val summaryModel = ExaminationSummaryModel()
                val code = it.code
                summaryModel.id = it.id
                summaryModel.code = code
                summaryModel.description = icdService.getByCode2(code).name2
                summaryModel.staffName = it.staffName
                summaryModel.value = it.value
                summaryModel
            }
            .toMutableList()
    )

    private fun mapToExaminationModel(examination: Examination): ExaminationModel = ExaminationModel(
        examination.id,
        DateUtils.fromDateTime(examination.date),
        examination.code,
        examination.patient.pesel,
        examination.patient.lastName,
        examination.patient.firstName,
        StringJoiner(" ")
            .add(examination.patient.address1)
            .add(examination.patient.address2)
            .toString(),
        examination.patient.phone
    )

    companion object {
        private val log = LoggerFactory.getLogger(ExaminationService::class.java)
    }
}
