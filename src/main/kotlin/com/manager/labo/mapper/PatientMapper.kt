package com.manager.labo.mapper

import com.manager.labo.entities.Patient
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.PatientModel
import com.manager.labo.utils.DateUtils
import org.springframework.stereotype.Component

@Component
class PatientMapper {

    fun toPatientModel(patient: Patient?): PatientModel? =
        if (patient == null) null
        else PatientModel(id = patient.id,
                          patient.pesel,
                          DateUtils.fromDate(patient.birth),
                          patient.lastName,
                          patient.firstName,
                          address1 = patient.address1,
                          address2 = patient.address2,
                          phone = patient.phone,
                          zipCode = patient.zipCode,
                          city = patient.city
        )

    fun fromPatientModel(model: PatientModel): Patient =
        Patient(id = model.id,
                firstName = model.firstName!!,
                lastName = model.lastName!!,
                pesel = model.pesel,
                address1 = model.address1!!,
                address2 = model.address2,
                city = model.city!!,
                zipCode = model.zipCode!!,
                phone = model.phone!!,
                birth = DateUtils.toDate(model.birthDay!!),
                examinations = mutableSetOf()
        )

    fun fromExaminationRequestModel(model: ExaminationRequestModel) =
        Patient(firstName = model.firstName!!,
                lastName = model.lastName!!,
                pesel = model.pesel,
                address1 = model.address1!!,
                address2 = when (val it = model.address2) {
                    ""   -> null
                    else -> it
                },
                city = model.city!!,
                zipCode = model.zipCode!!,
                phone = model.phone!!,
                birth = DateUtils.toDate(model.birthDay!!)
        )

    fun updateFromExaminationRequestMapper(patient: Patient, model: ExaminationRequestModel) =
        patient.copy(firstName = model.firstName!!,
                     lastName = model.lastName!!,
                     pesel = model.pesel,
                     address1 = model.address1!!,
                     address2 = when (val it = model.address2) {
                         ""   -> null
                         else -> it
                     },
                     city = model.city!!,
                     zipCode = model.zipCode!!,
                     birth = DateUtils.toDate(model.birthDay!!)
        )
}
