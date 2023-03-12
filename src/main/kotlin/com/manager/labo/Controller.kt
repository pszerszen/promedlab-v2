package com.manager.labo

import com.manager.labo.entities.Icd
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import com.manager.labo.service.ExaminationService
import com.manager.labo.service.IcdService
import com.manager.labo.service.PatientService
import com.manager.labo.utils.*
import com.manager.labo.validator.ExaminationRequestValidator
import com.manager.labo.view.*
import com.manager.labo.view.components.JPanelEnchancer
import org.apache.commons.collections4.CollectionUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.*
import kotlin.system.exitProcess

@Service
final class Controller(
    private val icdService: IcdService,
    private val patientService: PatientService,
    private val examinationService: ExaminationService,
    private val examinationRequestValidator: ExaminationRequestValidator,
    private val defaultSizeProvider: (Class<out DefaultSizeable>) -> Dimension
) : JFrame("PRO-LAB-MANAGER"), ActionListener, WindowListener {

    private val mainPanel: MainPanel by lazy { MainPanel() }
    private val examinationList: ExaminationListPanel by lazy { ExaminationListPanel() }
    private val patientList: PatientListPanel by lazy { PatientListPanel() }
    private lateinit var examinationDetails: ExaminationDetailsForm

    init {
        setApplicationIcon()

        JPanelEnchancer(mainPanel)
            .addAction(EXAMINATION_ADD) {
                examinationDetails = ExaminationDetailsForm()
                setExaminationDetailsActions()
            }
            .addAction(PATIENT_LIST) { setPatientList() }
            .addAction(EXAMINATION_LIST) { setExaminationList() }

        JPanelEnchancer(patientList)
            .addListeners(this, null)

        JPanelEnchancer(examinationList)
            .addListeners(this, null)

        setMainPanel()
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isVisible = true
    }

    override fun actionPerformed(e: ActionEvent) {
        log.debug("Action Performed: " + e.actionCommand)
        when (e.actionCommand) {
            "Patient-See" -> {
                patientList.currentModel.let {
                    examinationDetails = ExaminationDetailsForm()
                    examinationDetails.mountValuesFromModel(patientService.getById(it?.id!!))
                    setExaminationDetailsActions()
                }
            }

            "Examination-See" -> {
                examinationList.currentModel.let {
                    examinationDetails = ExaminationDetailsForm(examinationService.getExaminationRequestModel(it?.id!!))
                    setExaminationDetailsActions()
                }
            }

            BACK -> setMainPanel()
            "Patient-Reload" -> patientList.reloadTable(patientService.all)
            "Examination-Reload" -> examinationList.reloadTable(examinationService.all)
        }
    }

    private fun setMainPanel() {
        setCurrentPanel(mainPanel)
    }

    private fun setPatientList() {
        patientList.reloadTable(patientService.all)
        setCurrentPanel(patientList)
    }

    private fun setExaminationList() {
        examinationList.reloadTable(examinationService.all)
        setCurrentPanel(examinationList)
    }

    private fun setExaminationDetailsActions() {
        examinationDetails.initExaminationGroups(icdService.groups)
        examinationDetails.rewriteAvailableExaminations(icdService.getExaminationsFromGroup(examinationDetails.currentExaminationGroup))
        JPanelEnchancer(examinationDetails)
            .addAction(SWITCH_AVAILABLE_EXAMINATIONS) {
                examinationDetails.rewriteAvailableExaminations(icdService.getExaminationsFromGroup(examinationDetails.currentExaminationGroup))
            }
            .addAction(SEARCH_FOR_PATIENT) {
                patientService.getPatientModelByPesel(examinationDetails.pesel).let {
                    examinationDetails.mountValuesFromModel(it)
                }
            }
            .addAction(REMOVE_FROM_EXAMINATIONS) {
                examinationDetails.removeSelectedExaminationFromTable()
                examinationDetails.enableExaminationGroup(true)
            }
            .addAction(ADD_TO_EXAMINATIONS) {
                val icd: Icd = icdService.getByCode2(examinationDetails.currentExamination)
                examinationDetails.addExaminationDetail(ExaminationSummaryModel(icd.code2, icd.name2))
                examinationDetails.enableExaminationGroup(false)
            }
            .addAction(EXIT) { setMainPanel() }
            .addAction(EXAMINATION_SUBMIT) {
                examinationDetails.loadValuesToModel()
                val model: ExaminationRequestModel = examinationDetails.model
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
        setCurrentPanel(examinationDetails)
    }

    private fun setCurrentPanel(jPanel: DefaultSizeable) {
        contentPane = jPanel as JPanel
        this.size = defaultSizeProvider.invoke(jPanel.javaClass)
        repaint()
    }

    private fun setApplicationIcon() {
        iconImage = ImageIcon(javaClass.classLoader.getResource("assets/logo.png")?.path).image
        repaint()
    }

    override fun windowOpened(e: WindowEvent) {
        // NO ACTION
    }

    override fun windowClosing(e: WindowEvent) {
        e.window.dispose()
        this.dispose()
        exitProcess(EXIT_ON_CLOSE)
    }

    override fun windowClosed(e: WindowEvent) {
        // NO ACTION
    }

    override fun windowIconified(e: WindowEvent) {
        // NO ACTION
    }

    override fun windowDeiconified(e: WindowEvent) {
        // NO ACTION
    }

    override fun windowActivated(e: WindowEvent) {
        // NO ACTION
    }

    override fun windowDeactivated(e: WindowEvent) {
        // NO ACTION
    }

    companion object {
        private val log = LoggerFactory.getLogger(Controller::class.java)
    }
}
