package com.manager.labo.view;

import static com.manager.labo.utils.ActionCommandsKt.ADD_TO_EXAMINATIONS;
import static com.manager.labo.utils.ActionCommandsKt.EXAMINATION_SUBMIT;
import static com.manager.labo.utils.ActionCommandsKt.EXIT;
import static com.manager.labo.utils.ActionCommandsKt.REMOVE_FROM_EXAMINATIONS;
import static com.manager.labo.utils.ActionCommandsKt.SEARCH_FOR_PATIENT;
import static com.manager.labo.utils.ActionCommandsKt.SWITCH_AVAILABLE_EXAMINATIONS;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

import com.manager.labo.model.ExaminationRequestModel;
import com.manager.labo.model.ExaminationSummaryModel;
import com.manager.labo.model.PatientModel;
import com.manager.labo.utils.ActionCommand;
import com.manager.labo.utils.MappingField;
import com.manager.labo.view.components.JPanelEnchancer;
import com.manager.labo.view.components.LaboTableModel;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExaminationDetailsForm extends JPanel {

    private final ExaminationRequestModel model;
    private LaboTableModel<ExaminationSummaryModel> examinationTableModel;

    private JLabel nameLbl;
    private JLabel lastNameLbl;
    private JLabel birthdayLbl;
    private JLabel peselLbl;
    private JLabel addressLbl;
    private JLabel address2Lbl;
    private JLabel cityLbl;
    private JLabel zipCodeLbl;
    private JLabel phoneLbl;
    private JTextField firstName;
    private JTextField lastName;
    private JFormattedTextField birthDay;
    private JFormattedTextField pesel;
    @ActionCommand(SEARCH_FOR_PATIENT)
    private JButton searchForPatient;
    private JTextField address1;
    private JTextField address2;
    private JTextField city;
    private JFormattedTextField zipCode;
    private JTextField phone;
    private JLabel examinationGroupLbl;
    private JLabel examinationListLbl;
    @ActionCommand(SWITCH_AVAILABLE_EXAMINATIONS)
    private JComboBox<String> examinationGroup;
    private JComboBox<String> availableExamination;
    @ActionCommand(ADD_TO_EXAMINATIONS)
    private JButton addToExaminations;
    private JTable table;
    @ActionCommand(REMOVE_FROM_EXAMINATIONS)
    private JButton removeFromExaminations;
    private JLabel examinerLbl;
    private JTextField examiner;
    private JLabel examinationValueLbl;
    private JSpinner examinationValue;
    @ActionCommand(EXAMINATION_SUBMIT)
    private JButton submit;
    @ActionCommand(EXIT)
    private JButton exit;
    private JButton addExaminationValue;
    private JPanel topRightPanel;
    private JScrollPane scrollPane;
    private JPanel patientData;
    private JPanel mainPanel;

    public ExaminationDetailsForm(ExaminationRequestModel model) {
        super();
        this.model = model;
        addExaminationValue.addActionListener(e -> {
            for (int row : table.getSelectedRows()) {
                model.getExaminations().get(row).setStaffNameAndValue(examiner.getText(), (int) examinationValue.getValue());
            }
            mountValuesFromModel();
        });

        if (model != null) {
            mountValuesFromModel();

            disableComponents(firstName, lastName, pesel, zipCode, address1, address2, city, phone, examinationGroup, availableExamination,
                                 addToExaminations, removeFromExaminations, searchForPatient, birthDay);
        } else {
            disableComponents(examinerLbl, examiner, examinationValueLbl, examinationValue, addExaminationValue);
        }

        postCreateUIComponents();
    }

    @SneakyThrows
    private void createUIComponents() {
        birthDay = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
        pesel = new JFormattedTextField(new MaskFormatter("###########"));
        zipCode = new JFormattedTextField(new MaskFormatter("##-###"));

        examinationValue = new JSpinner();
        examinationValue.setModel(new SpinnerNumberModel(0, null, null, 1));
        examinationValue.setBounds(10, 100, 210, 20);
        var editor = examinationValue.getEditor();
        var formattedTextField = (JFormattedTextField) editor.getComponent(0);
        var formatter = (DefaultFormatter) formattedTextField.getFormatter();
        formatter.setCommitsOnValidEdit(true);

        mainPanel = new JPanel();
    }

    private void postCreateUIComponents() {
        add(mainPanel);
        setSize(mainPanel.getSize());
        new JPanelEnchancer(this).standardActions();
    }

    public ExaminationRequestModel getModel() {
        return model;
    }

    public void mountValuesFromModel(PatientModel patientModel) {
        mappingOperation(patientModel, this::setUpSwingComponentValues);
    }

    public void loadValuesToModel() {
        mappingOperation(model, this::setUpModelValues);

        model.getExaminations().clear();
        model.getExaminations().addAll(examinationTableModel.getModelList());
    }

    public void initExaminationGroups(List<String> groups) {
        groups.forEach(examinationGroup::addItem);
    }

    public String getCurrentExaminationGroup() {
        final String selectedItem = (String) examinationGroup.getSelectedItem();
        return selectedItem.substring(0, 1);
    }

    public void rewriteAvailableExaminations(List<String> examinations) {
        availableExamination.removeAllItems();
        examinations.forEach(availableExamination::addItem);
    }

    public String getPesel() {
        return pesel.getText();
    }

    public String getCurrentExamination() {
        final String selectedItem = (String) availableExamination.getSelectedItem();
        return selectedItem.substring(0, 3);
    }

    public void addExaminationDetail(ExaminationSummaryModel model) {
        examinationTableModel.addRow(model);
    }

    public void removeSelectedExamiantionFromTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow > -1) {
            examinationTableModel.removeRow(selectedRow);
        }
    }

    public void mountValuesFromModel(Object model) {
        mappingOperation(model, this::setUpSwingComponentValues);
    }

    public void enableExaminationGroup(boolean enable) {
        setComponentsEnabled(enable && examinationTableModel.getRowCount() < 1, examinationGroup);
    }

    private void disableComponents(JComponent... components) {
        setComponentsEnabled(false, components);
    }

    private void setComponentsEnabled(boolean enabled, JComponent... components) {
        Arrays.stream(components).forEach(c -> c.setEnabled(enabled));
    }

    private void mountValuesFromModel() {
        mappingOperation(model, this::setUpSwingComponentValues);

        examinationTableModel.setNumRows(0);
        examinationTableModel.addRows(model.getExaminations());
    }

    void mappingOperation(Object model, TriConsumer consumer) {
        try {
            for (var field : model.getClass().getDeclaredFields()) {
                var mappingField = field.getAnnotation(MappingField.class);
                if (mappingField != null) {
                    var mappingName = Optional.ofNullable(defaultIfEmpty(mappingField.value().trim(), null))
                        .orElse(field.getName());
                    field.setAccessible(true);
                    var swingField = this.getClass().getDeclaredField(mappingName);
                    swingField.setAccessible(true);
                    consumer.accept(model, field, (JTextComponent) swingField.get(this));
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            log.error("Error during mapping values with UI.", e);
        }
    }

    private void setUpModelValues(Object model, Field field, JTextComponent component) throws IllegalAccessException {
        field.set(model, component.getText());
    }

    void setUpSwingComponentValues(Object model, Field field, JTextComponent component) throws IllegalAccessException {
        component.setText(Optional.ofNullable(field.get(model))
                              .map(Object::toString)
                              .orElse(""));
    }

    @FunctionalInterface
    private interface TriConsumer {

        void accept(Object model, Field field, JTextComponent component) throws IllegalAccessException;
    }
}
