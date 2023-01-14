package com.manager.labo.model

import com.manager.labo.utils.DisplayInJTable
import com.manager.labo.utils.MappingField
import com.manager.labo.view.components.TableModelName

data class PatientModel(
    private var id: Long? = null,

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 0)
    val pesel: String? = null,

    @MappingField
    val birthDay: String? = null,

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 1)
    val lastName: String? = null,

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 2)
    val firstName: String? = null,

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 3)
    val address1: String? = null,

    @MappingField
    val address2: String? = null,

    @MappingField
    @DisplayInJTable(name = TableModelName.PATIENTS, order = 4)
    val phone: String? = null,

    @MappingField
    val zipCode: String? = null,

    @MappingField
    val city: String? = null
)
