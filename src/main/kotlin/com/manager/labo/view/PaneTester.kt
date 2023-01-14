package com.manager.labo.view

import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import com.manager.labo.view.components.JPanelEnchancer
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import javax.swing.JPanel

class PaneTester() : JFrame(), ActionListener, KeyListener {
    constructor(jPanel: JPanel) : this() {
        size = jPanel.size
        contentPane = jPanel
        JPanelEnchancer(jPanel).addListeners(this, this)
        isVisible = true
    }

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
    }

    override fun keyTyped(e: KeyEvent) {
        // TODO Auto-generated method stub
    }

    override fun keyPressed(e: KeyEvent) {
        // TODO Auto-generated method stub
    }

    override fun keyReleased(e: KeyEvent) {
        // TODO Auto-generated method stub
    }

    override fun actionPerformed(e: ActionEvent) {
        // TODO Auto-generated method stub
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val model = ExaminationRequestModel()
            model.address1 = "address1"
            model.address2 = "address2"
            model.city = "city"
            model.examinationId = 0L
            model.firstName = "name"
            model.lastName = "surname"
            model.pesel = "91080208596"
            model.phone = "798749030"
            model.zipCode = "20-570"
            model.examinations.add(ExaminationSummaryModel("A", "description A1"))
            model.examinations.add(ExaminationSummaryModel("A", "description A2"))
            model.examinations.add(ExaminationSummaryModel("A", "description A3"))
            model.examinations.add(ExaminationSummaryModel("B", "description B1"))
            model.examinations.add(ExaminationSummaryModel("B", "description B2"))
            model.examinations.add(ExaminationSummaryModel("B", "description B3"))
            model.examinations.add(ExaminationSummaryModel("C", "description C1"))
            model.examinations.add(ExaminationSummaryModel("C", "description C2"))
            model.examinations.add(ExaminationSummaryModel("C", "description C3"))
            val jPanel = ExaminationDetails(model)
            PaneTester(jPanel)
        }
    }
}
