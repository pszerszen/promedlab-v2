package com.manager.labo.view

import com.manager.labo.utils.BACK
import com.manager.labo.view.components.JPanelEnchancer
import com.manager.labo.view.components.LaboTableModel
import java.awt.Color
import java.awt.Dimension
import javax.swing.*

@Deprecated(message = "Use java swing components", replaceWith = ReplaceWith("com.manager.labo.view.AbstractListPanel"))
abstract class ListPanel<T : Any>(
    private val table: JTable = JTable()
) : JPanel() {

    protected var tableModel: LaboTableModel<T>? = null
    private var action: JButton
    private val reload: JButton
    private val back: JButton

    init {
        size = Dimension(1000, 570)
        minimumSize = Dimension(1000, 570)
        layout = null
        initTableModel()
        table.model = tableModel
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
        table.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        val scrollPane = JScrollPane(table)
        scrollPane.layout = ScrollPaneLayout()
        scrollPane.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        scrollPane.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        scrollPane.setBounds(10, 11, 980, 458)
        this.add(scrollPane)
        action = JButton(actionButtonText)
        action.background = Color(51, 204, 0)
        action.setBounds(671, 513, 170, 57)
        action.actionCommand = typePrefix + SEE
        this.add(action)
        reload = JButton("Przeładuj", ImageIcon(javaClass.classLoader.getResource("assets/redo.png")))
        reload.background = Color(255, 0, 0)
        reload.setBounds(851, 513, 139, 57)
        reload.actionCommand = typePrefix + RELOAD
        this.add(reload)
        back = JButton("Wróć", ImageIcon(javaClass.classLoader.getResource("assets/undo.png")))
        back.background = Color(0, 153, 255)
        back.setBounds(10, 513, 89, 57)
        back.actionCommand = BACK
        this.add(back)
        JPanelEnchancer(this).standardActions()
    }

    protected abstract fun initTableModel()
    protected abstract val typePrefix: String
    protected abstract val actionButtonText: String

    val currentModel: T?
        get() = tableModel?.getRowAsModel(table.selectedRow)

    fun reloadTable(models: List<T>) {
        tableModel?.rowCount = 0
        tableModel?.addRows(models)
    }

    companion object {
        private const val serialVersionUID = -3579109254796950348L
        private const val SEE = "See"
        private const val RELOAD = "Reload"
    }
}
