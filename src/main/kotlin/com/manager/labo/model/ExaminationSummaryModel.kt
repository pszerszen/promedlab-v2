package com.manager.labo.model

import com.manager.labo.utils.DisplayInJTable
import com.manager.labo.view.components.TableModel
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class ExaminationSummaryModel {
    var id: Long? = null

    @Pattern(regexp = "[A-Z]\\d{2}")
    @DisplayInJTable(name = TableModel.EXAMINATIONS_SET, order = 0)
    var code: String? = null

    @DisplayInJTable(name = TableModel.EXAMINATIONS_SET, order = 1)
    var description: String? = null

    @NotNull(message = "Nie wpisano wykonującego badania.")
    @DisplayInJTable(name = TableModel.EXAMINATIONS_SET, order = 2)
    var staffName: String? = null

    @NotNull(message = "Należy uzupełnić wartość badania.")
    @DisplayInJTable(name = TableModel.EXAMINATIONS_SET, order = 3)
    var value: Int? = null

    constructor()

    constructor(code: String?, description: String?) : this(
            id = null,
            code = code,
            description = description,
            staffName = null,
            value = null)

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
