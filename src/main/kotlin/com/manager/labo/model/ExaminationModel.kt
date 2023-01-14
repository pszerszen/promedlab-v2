package com.manager.labo.model

import com.manager.labo.utils.DisplayInJTable
import com.manager.labo.view.components.TableModelName

data class ExaminationModel(
    val id: Long,
    @DisplayInJTable(TableModelName.REQUESTS, 0)
    val requestDate: String,
    @DisplayInJTable(TableModelName.REQUESTS, 1)
    val code: String,
    @DisplayInJTable(TableModelName.REQUESTS, 2)
    val pesel: String,
    @DisplayInJTable(TableModelName.REQUESTS, 3)
    val lastName: String,
    @DisplayInJTable(TableModelName.REQUESTS, 4)
    val firstName: String,
    @DisplayInJTable(TableModelName.REQUESTS, 5)
    val address: String,
    @DisplayInJTable(TableModelName.REQUESTS, 6)
    val phone: String
)
