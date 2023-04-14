package com.manager.labo.mapper

import com.manager.labo.entities.Examination
import com.manager.labo.entities.ExaminationDetails
import com.manager.labo.model.ExaminationSummaryModel
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ExaminationDetailsMapper {

    fun fromExaminationSummaryModel(model: ExaminationSummaryModel,
                                    examinationSupplier: () -> Examination): ExaminationDetails =
        ExaminationDetails(code = model.code!!,
                           examination = examinationSupplier.invoke(),
                           date = LocalDateTime.now())

    fun updateFromExaminationSummaryModel(details: ExaminationDetails,
                                          model: ExaminationSummaryModel): ExaminationDetails =
        details.copy(staffName = model.staffName,
                     value = model.value)

    fun toExaminationSummaryModel(examinationDetails: ExaminationDetails,
                                  descriptionSupplier: (code: String) -> String): ExaminationSummaryModel =
        ExaminationSummaryModel(id = examinationDetails.id,
                                code = examinationDetails.code,
                                description = descriptionSupplier.invoke(examinationDetails.code),
                                staffName = examinationDetails.staffName,
                                value = examinationDetails.value)

}
