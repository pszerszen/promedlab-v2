package com.manager.labo.view

import com.manager.labo.view.components.LaboTableModel
import javax.swing.JTable

interface ListPanelLogic<T : Any> {
    val table: JTable
    val tableModel: LaboTableModel<T>

    val currentModel: T? get() = tableModel.getRowAsModel(table.selectedRow)

    fun reloadTable(models: List<T>) {
        tableModel.rowCount = 0
        tableModel.addRows(models)
    }

}
