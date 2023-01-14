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

    fun getByPesel(pesel: String): Patient = patientRepository.getByPesel(pesel)

    fun getPatientModelByPesel(pesel: String): PatientModel? = convert(getByPesel(pesel))

    fun getById(id: Long): PatientModel? = convert(get(id))

    val all: List<PatientModel?> get() = patientRepository.findAll().mapNotNull { this.convert(it) }


    private fun convert(patient: Patient?): PatientModel? {
        if (patient == null) {
            return null
        }
        val model = PatientModel()
        model.id = patient.id
        model.pesel = patient.pesel
        model.birthDay = patient.birth?.let { DateUtils.fromDate(it) }
        model.lastName = patient.lastName
        model.firstName = patient.firstName
        model.address1 = patient.address1
        model.address2 = patient.address2
        model.phone = patient.phone
        model.zipCode = patient.zipCode
        model.city = patient.city
        return model
    }

    private fun convert(model: PatientModel): Patient {
        val patient = Patient()
        patient.id = model.id
        patient.pesel = model.pesel
        patient.birth = model.birthDay?.let { DateUtils.toDate(it) }
        patient.lastName = model.lastName
        patient.firstName = model.firstName
        patient.address1 = model.address1
        patient.address2 = model.address2
        patient.phone = model.phone
        patient.zipCode = model.zipCode
        patient.city = model.city
        return patient
    }
}
