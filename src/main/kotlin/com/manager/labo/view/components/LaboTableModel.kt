package com.manager.labo.view.components

import com.manager.labo.utils.DisplayInJTable
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer
import javax.swing.table.DefaultTableModel

class LaboTableModel<M : Any>(
    private val name: TableModelName,
    vararg columns: String?,
    private val modelTable: MutableMap<Int, M> = ConcurrentHashMap()
) : DefaultTableModel(columns, 0) {

    override fun isCellEditable(row: Int, column: Int): Boolean = false

    override fun getColumnClass(columnIndex: Int): Class<String> = String::class.java

    override fun setRowCount(rowCount: Int) {
        modelTable.forEach { (key: Int, _: M) ->
            if (key >= rowCount) {
                modelTable.remove(key)
            }
        }
        super.setRowCount(rowCount)
    }

    fun addRows(models: List<M>) {
        models.forEach(Consumer { model: M -> this.addRow(model) })
    }

    fun getRowAsModel(row: Int): M? = modelTable[row]

    fun getRowsAsModels(vararg rows: Int): List<M?> = rows.map { getRowAsModel(it) }

    val modelList: List<M>
        get() {
            return modelTable.values.toList()
        }

    fun addRow(model: M) {
        val row: MutableMap<Int, String> = HashMap()
        model.javaClass.declaredFields.forEach { field ->
            field.isAccessible = true
            val displayInJTables = field.getAnnotationsByType<DisplayInJTable>(DisplayInJTable::class.java)
            for (displayInJTable in displayInJTables) {
                if (name == displayInJTable.name) {
                    try {
                        row[displayInJTable.order] = field.get(model).toString()
                    } catch (ignore: Exception) {
                    }
                }
            }
        }
        val tableRow = arrayOfNulls<Any>(row.size)
        (0 until row.size).forEach { tableRow[it] = row[it] }
        addRow(tableRow)
        modelTable[rowCount - 1] = model
    }

    override fun removeRow(row: Int) {
        modelTable.remove(row)
        super.removeRow(row)
    }

}