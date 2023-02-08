package com.manager.labo

import com.manager.labo.entities.Icd
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import com.manager.labo.service.ExaminationService
import com.manager.labo.service.IcdService
import com.manager.labo.service.PatientService
import com.manager.labo.utils.*
import com.manager.labo.validator.ExaminationRequestValidator
import com.manager.labo.view.ExaminationDetailsForm
import com.manager.labo.view.ExaminationListPanel
import com.manager.labo.view.MainPanel
import com.manager.labo.view.PatientListPanel
import com.manager.labo.view.components.JPanelEnchancer
import org.apache.commons.collections4.CollectionUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.*
import kotlin.system.exitProcess

@Service
class Controller(
    private val icdService: IcdService,
    private val patientService: PatientService,
    private val examinationService: ExaminationService,
    private val examinationRequestValidator: ExaminationRequestValidator
) : JFrame("PRO-LAB-MANAGER"), ActionListener, WindowListener {
    private var mainPanel: MainPanel? = null
    private var examinationList: ExaminationListPanel? = null
    private var patientList: PatientListPanel? = null
    private var examinationDetails: ExaminationDetailsForm? = null


    init {
        setMainPanel()
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isVisible = true
    }

    override fun actionPerformed(e: ActionEvent) {
        log.debug("Action Performed: " + e.actionCommand)
        when (e.actionCommand) {
            "Patient-See" -> {
                patientList?.currentModel.let {
                    examinationDetails = ExaminationDetailsForm()
                    examinationDetails!!.mountValuesFromModel(patientService.getById(it?.id!!))
                    setExaminationDetailsActions()
                }
            }

            "Examination-See" -> {
                examinationList?.currentModel.let {
                    examinationDetails = ExaminationDetailsForm(examinationService.getExaminationRequestModel(it?.id!!))
                    setExaminationDetailsActions()
                }
            }

            BACK -> setMainPanel()
            "Patient-Reload" -> patientList?.reloadTable(patientService.all)
            "Examination-Reload" -> examinationList?.reloadTable(examinationService.all)
        }
    }

    private fun setMainPanel() {
        if (mainPanel == null) {
            mainPanel = MainPanel()
            JPanelEnchancer(mainPanel!!)
                .addAction(EXAMINATION_ADD) {
                    examinationDetails = ExaminationDetailsForm()
                    setExaminationDetailsActions()
                }
                .addAction(PATIENT_LIST) { setPatientList() }
                .addAction(EXAMINATION_LIST) { setExaminationList() }
        }
        setCurrentPanel(mainPanel!!)
    }

    private fun setPatientList() {
        if (patientList == null) {
            patientList = PatientListPanel()
            JPanelEnchancer(patientList!!)
                .addListeners(this, null)
        }
        patientList?.reloadTable(patientService.all)
        setCurrentPanel(patientList!!)
    }

    private fun setExaminationList() {
        if (examinationList == null) {
            examinationList = ExaminationListPanel()
            JPanelEnchancer(examinationList!!)
                .addListeners(this, null)
        }
        examinationList?.reloadTable(examinationService.all)
        setCurrentPanel(examinationList!!)
    }

    private fun setExaminationDetailsActions() {
        examinationDetails
        if (examinationDetails != null) {
            examinationDetails!!.initExaminationGroups(icdService.groups)
            examinationDetails!!.rewriteAvailableExaminations(icdService.getExaminationsFromGroup(examinationDetails!!.currentExaminationGroup))
            JPanelEnchancer(examinationDetails!!)
                .addAction(SWITCH_AVAILABLE_EXAMINATIONS) {
                    examinationDetails!!.rewriteAvailableExaminations(icdService.getExaminationsFromGroup(examinationDetails!!.currentExaminationGroup))
                }
                .addAction(SEARCH_FOR_PATIENT) {
                    patientService.getPatientModelByPesel(examinationDetails!!.pesel).let {
                        examinationDetails!!.mountValuesFromModel(it)
                    }
                }
                .addAction(REMOVE_FROM_EXAMINATIONS) {
                    examinationDetails!!.removeSelectedExamiantionFromTable()
                    examinationDetails!!.enableExaminationGroup(true)
                }
                .addAction(ADD_TO_EXAMINATIONS) {
                    val icd: Icd = icdService.getByCode2(examinationDetails!!.currentExamination)
                    examinationDetails!!.addExaminationDetail(ExaminationSummaryModel(icd.code2, icd.name2))
                    examinationDetails!!.enableExaminationGroup(false)
                }
                .addAction(EXIT) { setMainPanel() }
                .addAction(EXAMINATION_SUBMIT) {
                    examinationDetails!!.loadValuesToModel()
                    val model: ExaminationRequestModel = examinationDetails!!.model
                    var errors: Set<String?> = HashSet()
                    val newExamination = model.examinationId == null
                    try {
                        errors = examinationRequestValidator.validate(model, !newExamination)
                        if (CollectionUtils.isNotEmpty(errors)) {
                            val panel = JPanel()
                            panel.add(JList(errors.toTypedArray()))
                            JOptionPane.showMessageDialog(this, panel, "Błędy walidacji.", JOptionPane.ERROR_MESSAGE)
                        }
                    } catch (ex: Exception) {
                        log.error("Error while validating:", ex)
                    }
                    if (errors.isEmpty()) {
                        if (newExamination) {
                            examinationService.create(model)
                        } else {
                            examinationService.update(model)
                        }
                        setExaminationList()
                    }
                }
        }
        setCurrentPanel(examinationDetails!!)
    }

    private fun setCurrentPanel(jPanel: JPanel) {
        contentPane = jPanel
        setSize(jPanel.width + 50, jPanel.height + 50)
        repaint()
    }

    override fun windowOpened(e: WindowEvent) {}
    override fun windowClosing(e: WindowEvent) {
        e.window.dispose()
        this.dispose()
        exitProcess(EXIT_ON_CLOSE)
    }

    override fun windowClosed(e: WindowEvent) {}
    override fun windowIconified(e: WindowEvent) {}
    override fun windowDeiconified(e: WindowEvent) {}
    override fun windowActivated(e: WindowEvent) {}
    override fun windowDeactivated(e: WindowEvent) {}

    companion object {
        private val log = LoggerFactory.getLogger(Controller::class.java)
    }
}
