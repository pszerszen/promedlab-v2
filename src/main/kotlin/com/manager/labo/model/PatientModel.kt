package com.manager.labo.model

import com.manager.labo.utils.DisplayInJTable
import com.manager.labo.utils.MappingField
import com.manager.labo.view.components.TableModel

class PatientModel {
    var id: Long? = null
    @MappingField
    @DisplayInJTable(name = TableModel.PATIENTS, order = 0)
    var pesel: String? = null
    @MappingField
    var birthDay: String? = null
    @MappingField
    @DisplayInJTable(name = TableModel.PATIENTS, order = 1)
    var lastName: String? = null
    @MappingField
    @DisplayInJTable(name = TableModel.PATIENTS, order = 2)
    var firstName: String? = null
    @MappingField
    @DisplayInJTable(name = TableModel.PATIENTS, order = 3)
    var address1: String? = null
    @MappingField
    var address2: String? = null
    @MappingField
    @DisplayInJTable(name = TableModel.PATIENTS, order = 4)
    var phone: String? = null
    @MappingField
    var zipCode: String? = null
    @MappingField
    var city: String? = null

    constructor()

    constructor(
        id: Long?,
        pesel: String?,
        birthDay: String?,
        lastName: String?,
        firstName: String?,
        address1: String?,
        address2: String?,
        phone: String?,
        zipCode: String?,
        city: String?
    ) {
        this.id = id
        this.pesel = pesel
        this.birthDay = birthDay
        this.lastName = lastName
        this.firstName = firstName
        this.address1 = address1
        this.address2 = address2
        this.phone = phone
        this.zipCode = zipCode
        this.city = city
    }
}
