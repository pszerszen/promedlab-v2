package com.manager.labo.model

import com.manager.labo.utils.DisplayInJTable
import com.manager.labo.utils.MappingField
import com.manager.labo.view.components.TableModelName

class PatientModel {
    var id: Long? = null

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 0)
    var pesel: String? = null

    @MappingField
    var birthDay: String? = null

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 1)
    var lastName: String? = null

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 2)
    var firstName: String? = null

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 3)
    var address1: String? = null

    @MappingField
    var address2: String? = null

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 4)
    var phone: String? = null

    @MappingField
    var zipCode: String? = null

    @MappingField
    var city: String? = null
}
