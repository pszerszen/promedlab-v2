package com.manager.labo.mapper

import com.manager.labo.entities.Examination
import com.manager.labo.entities.ExaminationDetails
import com.manager.labo.model.ExaminationSummaryModel
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ExaminationDetailsMapper {

    fun fromExaminationSummaryModel(model: ExaminationSummaryModel, examinationSupplier: () -> Examination): ExaminationDetails =
        ExaminationDetails(
            code = model.code!!,
            examination = examinationSupplier.invoke(),
            date = LocalDateTime.now())

    fun updateFromExaminationSummaryModel(details: ExaminationDetails, model: ExaminationSummaryModel): ExaminationDetails =
        details.copy(
            staffName = model.staffName,
            value = model.value)

    fun toExaminationSummaryModel(examinationDetails: ExaminationDetails, descriptionSupplier: (code: String) -> String): ExaminationSummaryModel =
        ExaminationSummaryModel(
            examinationDetails.id,
            examinationDetails.code,
            descriptionSupplier.invoke(examinationDetails.code),
            examinationDetails.staffName,
            examinationDetails.value)

}
