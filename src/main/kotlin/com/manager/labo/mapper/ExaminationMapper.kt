package com.manager.labo.mapper

import com.manager.labo.entities.Examination
import com.manager.labo.entities.Patient
import com.manager.labo.model.ExaminationModel
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import com.manager.labo.utils.DateUtils
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class ExaminationMapper {

    fun toExaminationModel(examination: Examination): ExaminationModel =
        ExaminationModel(
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

    fun toExaminationRequestModel(
        examination: Examination,
        examinationDetailsSupplier: () -> MutableList<ExaminationSummaryModel>
    ): ExaminationRequestModel =
        ExaminationRequestModel(
            examinationId = examination.id,
            firstName = examination.patient.firstName,
            lastName = examination.patient.lastName,
            pesel = examination.patient.pesel,
            birthDay = DateUtils.fromDate(examination.patient.birth),
            address1 = examination.patient.address1,
            address2 = examination.patient.address2,
            zipCode = examination.patient.zipCode,
            city = examination.patient.city,
            phone = examination.patient.phone,
            examinations = examinationDetailsSupplier.invoke()
        )

    fun fromExaminationRequestModel(model: ExaminationRequestModel, patientSupplier: () -> Patient): Examination =
        Examination(
            id = null,
            patient = patientSupplier.invoke(),
            date = LocalDateTime.now(),
            code = model.examinations[0].code!!.substring(0, 1),
            examinationDetails = mutableSetOf()
        )

    fun updateFromExaminationRequestModel(examination: Examination, model: ExaminationRequestModel): Examination =
        examination.copy(
            date = LocalDateTime.now(),
            code = model.examinations[0].code!!.substring(0, 1)
        )
}
