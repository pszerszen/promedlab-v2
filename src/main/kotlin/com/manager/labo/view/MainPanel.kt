package com.manager.labo.view

import com.manager.labo.utils.ActionCommand
import com.manager.labo.utils.EXAMINATION_ADD
import com.manager.labo.utils.EXAMINATION_LIST
import com.manager.labo.utils.PATIENT_LIST
import com.manager.labo.view.components.JPanelEnchancer
import javax.swing.JButton
import javax.swing.JPanel

class MainPanel : JPanel() {
    @ActionCommand(EXAMINATION_ADD)
    private var newRequest: JButton? = null

    @ActionCommand(PATIENT_LIST)
    private var patientsLists: JButton? = null

    @ActionCommand(EXAMINATION_LIST)
    private var examinationList: JButton? = null
    private fun createUIComponents() {
        newRequest = JButton()
        patientsLists = JButton()
        examinationList = JButton()
        JPanelEnchancer(this).standardActions().initButtonsActionCommands()
    }
}
