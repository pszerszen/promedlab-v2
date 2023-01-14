package com.manager.labo.model

import com.manager.labo.utils.DisplayInJTable
import com.manager.labo.view.components.TableModelName

data class ExaminationSummaryModel(
    val id: Long,
    @DisplayInJTable(name = TableModelName.EXAMINATIONS_SET, order = 0)
    val code: String,
    @DisplayInJTable(name = TableModelName.EXAMINATIONS_SET, order = 1)
    val description: String,
    @DisplayInJTable(name = TableModelName.EXAMINATIONS_SET, order = 2)
    val staffName: String,
    @DisplayInJTable(name = TableModelName.EXAMINATIONS_SET, order = 3)
    val value: Int
)
