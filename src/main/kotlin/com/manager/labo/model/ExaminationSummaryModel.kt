package com.manager.labo.model

import com.manager.labo.utils.DisplayInJTable
import com.manager.labo.view.components.TableModelName

class ExaminationSummaryModel {
    var id: Long? = null

    @DisplayInJTable(name = TableModelName.EXAMINATIONS_SET, order = 0)
    var code: String? = null

    @DisplayInJTable(name = TableModelName.EXAMINATIONS_SET, order = 1)
    var description: String? = null

    @DisplayInJTable(name = TableModelName.EXAMINATIONS_SET, order = 2)
    var staffName: String? = null

    @DisplayInJTable(name = TableModelName.EXAMINATIONS_SET, order = 3)
    var value: Int? = null

    constructor(){}

    constructor(code: String?, description: String?) {
        this.code = code
        this.description = description
    }

    constructor(id: Long?, code: String?, description: String?, staffName: String?, value: Int?) {
        this.id = id
        this.code = code
        this.description = description
        this.staffName = staffName
        this.value = value
    }

    fun setStaffNameAndValue(staffName: String, value: Int) {
        this.staffName = staffName
        this.value = value
    }
}
