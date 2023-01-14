package com.manager.labo.model

data class ExaminationRequestModel(
    val examinationId: Long,
    val firstName: String,
    val lastName: String,
    val pesel: String,
    val birthDay: String,
    val address1: String,
    val address2: String,
    val zipCode: String,
    val city: String,
    val phone: String,
    val examinations: List<ExaminationSummaryModel>
)
