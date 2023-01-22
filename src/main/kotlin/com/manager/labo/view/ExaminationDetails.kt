package com.manager.labo.view

import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import com.manager.labo.model.PatientModel
import com.manager.labo.utils.*
import com.manager.labo.view.components.JPanelEnchancer
import com.manager.labo.view.components.LaboTableModel
import com.manager.labo.view.components.TableModelName
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import java.awt.Dimension
import java.lang.reflect.Field
import java.util.*
import javax.swing.*
import javax.swing.border.TitledBorder
import javax.swing.text.DefaultFormatter
import javax.swing.text.JTextComponent

class ExaminationDetails @JvmOverloads constructor(model: ExaminationRequestModel? = null) : JPanel() {
    private val model: ExaminationRequestModel
    private val examinationTableModel: LaboTableModel<ExaminationSummaryModel>
    private val firstName: JTextField
    private val lastName: JTextField
    private val pesel: JTextField
    private val zipCode: JTextField
    private val address2: JTextField
    private val address1: JTextField
    private val city: JTextField
    private val phone: JTextField
    private val lblListaBada: JLabel
    private val table: JTable
    private val availableExamination: JComboBox<String>

    @ActionCommand(ADD_TO_EXAMINATIONS)
    private val addToExaminations: JButton

    @ActionCommand(REMOVE_FROM_EXAMINATIONS)
    private val removeFromExaminations: JButton
    private val lblTelefon: JLabel
    private val lblMiasto: JLabel
    private val lblKodPocztowy: JLabel
    private val lblAdresCd: JLabel
    private val lblAdres: JLabel
    private val lblPesel: JLabel
    private val lblNazwisko: JLabel
    private val lblImi: JLabel

    @ActionCommand(SEARCH_FOR_PATIENT)
    private val searchForPatient: JButton

    @ActionCommand(SWITCH_AVAILABLE_EXAMINATIONS)
    private val examinationGroup: JComboBox<String>
    private val birthDay: JTextField

    @ActionCommand(EXAMINATION_SUBMIT)
    private val submit: JButton

    @ActionCommand(EXIT)
    private val exit: JButton
    private val lblWykonujcyBadanie: JLabel
    private val examiner: JTextField
    private val lblWynikBadania: JLabel
    private val examinationValue: JSpinner
    private val addExaminationValue: JButton
    private val lblDataUrodzenia: JLabel
    private val lblGrupaBada: JLabel

    init {
        size = Dimension(1000, 700)
        minimumSize = Dimension(1200, 700)
        layout = null
        lblImi = JLabel("Imię")
        lblImi.setBounds(10, 31, 80, 14)
        add(lblImi)
        firstName = JTextField()
        firstName.setBounds(109, 28, 120, 20)
        add(firstName)
        firstName.columns = 10
        lblNazwisko = JLabel("Nazwisko")
        lblNazwisko.setBounds(10, 56, 80, 14)
        add(lblNazwisko)
        lastName = JTextField()
        lastName.setBounds(109, 53, 120, 20)
        add(lastName)
        lastName.columns = 10
        lblPesel = JLabel("PESEL")
        lblPesel.setBounds(10, 109, 80, 14)
        add(lblPesel)
        pesel = JTextField()
        pesel.setBounds(109, 106, 120, 20)
        add(pesel)
        pesel.columns = 10
        lblAdres = JLabel("Adres")
        lblAdres.setBounds(313, 31, 80, 14)
        add(lblAdres)
        lblAdresCd = JLabel("Adres cd")
        lblAdresCd.setBounds(313, 56, 80, 14)
        add(lblAdresCd)
        lblKodPocztowy = JLabel("Kod Pocztowy")
        lblKodPocztowy.setBounds(313, 81, 80, 14)
        add(lblKodPocztowy)
        lblMiasto = JLabel("Miasto")
        lblMiasto.setBounds(511, 81, 80, 14)
        add(lblMiasto)
        zipCode = JTextField()
        zipCode.setBounds(403, 78, 86, 20)
        add(zipCode)
        zipCode.columns = 10
        address2 = JTextField()
        address2.setBounds(403, 53, 188, 20)
        add(address2)
        address2.columns = 10
        address1 = JTextField()
        address1.setBounds(403, 28, 188, 20)
        add(address1)
        address1.columns = 10
        city = JTextField()
        city.setBounds(558, 78, 149, 20)
        add(city)
        city.columns = 10
        lblTelefon = JLabel("Telefon")
        lblTelefon.setBounds(313, 106, 80, 14)
        add(lblTelefon)
        phone = JTextField()
        phone.setBounds(403, 103, 120, 20)
        add(phone)
        phone.columns = 10
        lblListaBada = JLabel("Lista badań")
        lblListaBada.setBounds(10, 222, 80, 14)
        add(lblListaBada)
        availableExamination = JComboBox<String>()
        availableExamination.setBounds(104, 219, 603, 20)
        add(availableExamination)
        addToExaminations = JButton("Dodaj do zlecenia")
        addToExaminations.setBounds(717, 218, 230, 23)
        add(addToExaminations)
        val scrollPane = JScrollPane()
        scrollPane.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        scrollPane.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        scrollPane.border = TitledBorder(null, "Lista zleconych bada\u0144", TitledBorder.LEADING, TitledBorder.TOP, null, null)
        scrollPane.setBounds(10, 269, 697, 246)
        add(scrollPane)
        examinationTableModel = LaboTableModel(TableModelName.EXAMINATIONS_SET, "Kod badania", "Nazwa Badania", "Badający", "Wynik")
        table = JTable(examinationTableModel)
        scrollPane.setViewportView(table)
        removeFromExaminations = JButton("Usuń wybrane badanie z listy")
        removeFromExaminations.setBounds(717, 281, 230, 23)
        add(removeFromExaminations)
        this.model = Optional.ofNullable<ExaminationRequestModel>(model).orElse(ExaminationRequestModel())
        searchForPatient = JButton("<html>Szukaj<br/>pacjenta</html>")
        searchForPatient.setBounds(109, 130, 120, 37)
        add(searchForPatient)
        lblGrupaBada = JLabel("Grupa badań")
        lblGrupaBada.setBounds(10, 194, 80, 14)
        add(lblGrupaBada)
        examinationGroup = JComboBox<String>()
        examinationGroup.setBounds(104, 191, 603, 20)
        add(examinationGroup)
        lblDataUrodzenia = JLabel("Data Urodzenia")
        lblDataUrodzenia.setBounds(10, 81, 123, 14)
        add(lblDataUrodzenia)
        birthDay = JTextField()
        birthDay.setBounds(109, 78, 120, 20)
        add(birthDay)
        birthDay.columns = 10
        submit = JButton("Zapisz")
        submit.setBounds(717, 541, 89, 23)
        add(submit)
        exit = JButton("Wyjdź")
        exit.setBounds(717, 575, 89, 23)
        add(exit)
        val panel = JPanel()
        panel.border = TitledBorder(null, "Wprowad\u017A wynik badania", TitledBorder.LEFT, TitledBorder.TOP, null, null)
        panel.setBounds(717, 315, 230, 207)
        add(panel)
        panel.layout = null
        lblWykonujcyBadanie = JLabel("Wykonujący badanie")
        lblWykonujcyBadanie.setBounds(10, 24, 210, 14)
        panel.add(lblWykonujcyBadanie)
        examiner = JTextField()
        examiner.setBounds(10, 44, 210, 20)
        panel.add(examiner)
        examiner.columns = 10
        lblWynikBadania = JLabel("Wynik badania")
        lblWynikBadania.setBounds(10, 75, 220, 14)
        panel.add(lblWynikBadania)
        examinationValue = JSpinner()
        examinationValue.model = SpinnerNumberModel(0, null, null, 1)
        examinationValue.setBounds(10, 100, 210, 20)
        val editor: JComponent = examinationValue.editor
        val formattedTextField: JFormattedTextField = editor.getComponent(0) as JFormattedTextField
        val formatter = formattedTextField.formatter as DefaultFormatter
        formatter.commitsOnValidEdit = true
        panel.add(examinationValue)
        addExaminationValue = JButton("Dodaj wynik badań")
        addExaminationValue.setBounds(10, 173, 210, 23)
        addExaminationValue.addActionListener {
            for (row in table.selectedRows) {
                model?.examinations?.get(row)?.setStaffNameAndValue(examiner.text, examinationValue.value as Int)
            }
            mountValuesFromModel()
        }
        panel.add(addExaminationValue)
        if (model != null) {
            mountValuesFromModel()
            setComponentsEnabled(
                false, firstName, lastName, pesel, zipCode, address1, address2, city, phone, examinationGroup, availableExamination,
                addToExaminations, removeFromExaminations, searchForPatient, birthDay
            )
        } else {
            setComponentsEnabled(false, lblWykonujcyBadanie, examiner, lblWynikBadania, examinationValue, addExaminationValue)
        }
        JPanelEnchancer(this).standardActions()
    }

    fun getModel(): ExaminationRequestModel {
        return model
    }

    fun mountValuesFromModel(patientModel: PatientModel) {
        mappingOperation(patientModel) { model: Any, field: Field, component: JTextComponent -> setUpSwingComponentValues(model, field, component) }
    }

    fun loadValuesToModel() {
        mappingOperation(model) { model: Any, field: Field, component: JTextComponent -> setUpModelValues(model, field, component) }
        model.examinations.clear()
        model.examinations.addAll(examinationTableModel.modelList)
    }

    fun initExaminationGroups(groups: List<String?>) {
        groups.stream().forEach { item: String? -> examinationGroup.addItem(item) }
    }

    val currentExaminationGroup: String
        get() {
            val selectedItem = examinationGroup.selectedItem as String
            return selectedItem.substring(0, 1)
        }

    fun rewriteAvailableExaminations(examinations: List<String?>) {
        availableExamination.removeAllItems()
        examinations.stream().forEach { item: String? -> availableExamination.addItem(item) }
    }

    fun getPesel(): String {
        return pesel.text
    }

    val currentExamination: String
        get() {
            val selectedItem = availableExamination.selectedItem as String
            return selectedItem.substring(0, 3)
        }

    fun addExaminationDetail(model: ExaminationSummaryModel) {
        examinationTableModel.addRow(model)
    }

    fun removeSelectedExamiantionFromTable() {
        val selectedRow: Int = table.selectedRow
        if (selectedRow > -1) {
            examinationTableModel.removeRow(selectedRow)
        }
    }

    fun mountValuesFromModel(model: Any) {
        mappingOperation(model) { model: Any, field: Field, component: JTextComponent -> setUpSwingComponentValues(model, field, component) }
    }

    fun enableExaminationGroup(enable: Boolean) {
        setComponentsEnabled(enable && examinationTableModel.rowCount < 1, examinationGroup)
    }

    private fun setComponentsEnabled(enabled: Boolean, vararg components: JComponent) {
        for (component in components) {
            component.isEnabled = enabled
        }
    }

    private fun mountValuesFromModel() {
        mappingOperation(model) { model: Any, field: Field, component: JTextComponent -> setUpSwingComponentValues(model, field, component) }
        examinationTableModel.setNumRows(0)
        examinationTableModel.addRows(model.examinations)
    }

    private fun mappingOperation(model: Any, consumer: TriConsumer) {
        try {
            for (field in model.javaClass.declaredFields) {
                val mappingField: MappingField? = field.getAnnotation(MappingField::class.java)
                if (mappingField != null) {
                    val mappingName: String = Optional.of(mappingField.value.trim { it <= ' ' })
                        .filter { StringUtils.isNotBlank(it) }
                        .orElse(field.name)
                    field.isAccessible = true
                    val swingField = this.javaClass.getDeclaredField(mappingName)
                    swingField.isAccessible = true
                    consumer.accept(model, field, swingField[this] as JTextComponent)
                }
            }
        } catch (e: SecurityException) {
            log.error("Error during mapping values with UI.", e)
        } catch (e: IllegalArgumentException) {
            log.error("Error during mapping values with UI.", e)
        } catch (e: IllegalAccessException) {
            log.error("Error during mapping values with UI.", e)
        } catch (e: NoSuchFieldException) {
            log.error("Error during mapping values with UI.", e)
        }
    }

        private fun setUpModelValues(model: Any, field: Field, component: JTextComponent) {
            try {
                field[model] = component.text
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }

        private fun setUpSwingComponentValues(model: Any, field: Field, component: JTextComponent) {
            val `object`: Any?
            try {
                `object` = field[model]
                component.text = `object`?.toString() ?: ""
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }

        private fun interface TriConsumer {
            fun accept(model: Any, field: Field, component: JTextComponent)
        }

        companion object {
            private val log = LoggerFactory.getLogger(ExaminationDetails::class.java)
        }
    }
