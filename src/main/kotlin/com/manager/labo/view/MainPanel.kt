package com.manager.labo.view

import com.manager.labo.utils.*
import com.manager.labo.view.components.JPanelEnchancer
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JPanel

class MainPanel : JPanel() {
    @ActionCommand(EXAMINATION_ADD)
    private val btnNewRequest: JButton

    @ActionCommand(PATIENT_LIST)
    private val btnPatientsLists: JButton

    @ActionCommand(EXAMINATION_LIST)
    private val btnExaminationList: JButton

    init {
        size = Dimension(440, 115)
        layout = null
        btnNewRequest = JButton("Nowe zlecenie")
        btnNewRequest.setBounds(10, 49, 131, 23)
        add(btnNewRequest)
        btnPatientsLists = JButton("Lista Pacjentów")
        btnPatientsLists.setBounds(151, 49, 131, 23)
        add(btnPatientsLists)
        btnExaminationList = JButton("Lista Badań")
        btnExaminationList.setBounds(292, 49, 131, 23)
        add(btnExaminationList)
        JPanelEnchancer(this).standardActions().initButtonsActionCommands()
    }
}
