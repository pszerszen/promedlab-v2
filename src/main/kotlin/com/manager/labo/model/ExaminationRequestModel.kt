package com.manager.labo.model

class ExaminationRequestModel {
    var examinationId: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var pesel: String? = null
    var birthDay: String? = null
    var address1: String? = null
    var address2: String? = null
    var zipCode: String? = null
    var city: String? = null
    var phone: String? = null
    var examinations: MutableList<ExaminationSummaryModel> = mutableListOf()
}
