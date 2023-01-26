package com.manager.labo.service

import com.manager.labo.entities.Patient
import com.manager.labo.model.PatientModel
import com.manager.labo.repository.PatientRepository
import com.manager.labo.utils.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author pszerszen
 */
@Service
@Transactional
class PatientService(@Autowired val patientRepository: PatientRepository) {

    fun create(model: PatientModel): Long? = patientRepository.save(convert(model)).id

    fun update(model: PatientModel): Patient = patientRepository.save<Patient>(convert(model))

    fun create(patient: Patient): Long? = patientRepository.save<Patient>(patient).id

    fun update(patient: Patient): Patient = patientRepository.save<Patient>(patient)

    fun get(id: Long): Patient? = patientRepository.findByIdOrNull(id)

    fun getByPesel(pesel: String): Patient? = patientRepository.getByPesel(pesel)

    fun getPatientModelByPesel(pesel: String): PatientModel? = convert(getByPesel(pesel))

    fun getById(id: Long): PatientModel? = convert(get(id))

    val all: List<PatientModel> get() = patientRepository.findAll().mapNotNull { this.convert(it) }


    private fun convert(patient: Patient?): PatientModel? {
        return when (patient) {
            null -> {
                null
            }
            else -> PatientModel(
                patient.id,
                patient.pesel,
                DateUtils.fromDate(patient.birth),
                patient.lastName,
                patient.firstName,
                patient.address1,
                patient.address2,
                patient.phone,
                patient.zipCode,
                patient.city)
        }
    }

    private fun convert(model: PatientModel): Patient {
        return Patient(
            model.id,
            model.firstName!!,
            model.lastName!!,
            model.pesel,
            model.address1!!,
            model.address2,
            model.city!!,
            model.zipCode!!,
            model.phone!!,
            DateUtils.toDate(model.birthDay!!),
            emptySet()
        )
    }
}
