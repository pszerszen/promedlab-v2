package com.manager.labo.model

import com.manager.labo.utils.DisplayInJTable
import com.manager.labo.view.components.TableModelName

class ExaminationModel {
    var id: Long? = null

    @DisplayInJTable(TableModelName.REQUESTS, 0)
    var requestDate: String? = null

    @DisplayInJTable(TableModelName.REQUESTS, 1)
    var code: String? = null

    @DisplayInJTable(TableModelName.REQUESTS, 2)
    var pesel: String? = null

    @DisplayInJTable(TableModelName.REQUESTS, 3)
    var lastName: String? = null

    @DisplayInJTable(TableModelName.REQUESTS, 4)
    var firstName: String? = null

    @DisplayInJTable(TableModelName.REQUESTS, 5)
    var address: String? = null

    @DisplayInJTable(TableModelName.REQUESTS, 6)
    var phone: String? = null

    constructor()

    constructor(
        id: Long?,
        requestDate: String?,
        code: String?,
        pesel: String?,
        lastName: String?,
        firstName: String?,
        address: String?,
        phone: String?
    ) {
        this.id = id
        this.requestDate = requestDate
        this.code = code
        this.pesel = pesel
        this.lastName = lastName
        this.firstName = firstName
        this.address = address
        this.phone = phone
    }

}
