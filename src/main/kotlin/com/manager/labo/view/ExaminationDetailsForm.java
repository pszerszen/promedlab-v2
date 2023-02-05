package com.manager.labo.view;

import static com.manager.labo.utils.ActionCommandsKt.ADD_TO_EXAMINATIONS;
import static com.manager.labo.utils.ActionCommandsKt.EXAMINATION_SUBMIT;
import static com.manager.labo.utils.ActionCommandsKt.EXIT;
import static com.manager.labo.utils.ActionCommandsKt.REMOVE_FROM_EXAMINATIONS;
import static com.manager.labo.utils.ActionCommandsKt.SEARCH_FOR_PATIENT;
import static com.manager.labo.utils.ActionCommandsKt.SWITCH_AVAILABLE_EXAMINATIONS;

import com.manager.labo.model.ExaminationRequestModel;
import com.manager.labo.model.ExaminationSummaryModel;
import com.manager.labo.utils.ActionCommand;
import com.manager.labo.view.components.LaboTableModel;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;
import lombok.SneakyThrows;

public class ExaminationDetailsForm extends JPanel {
    private ExaminationRequestModel model;
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
    private JComboBox<String> availableExaminations;
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
    private JPanel panel;
    private JScrollPane scrollPane;
    private JPanel patientData;

    @SneakyThrows
    private void createUIComponents() {
        birthDay = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
        pesel = new JFormattedTextField(new MaskFormatter("###########"));
        zipCode = new JFormattedTextField(new MaskFormatter("##-###"));
    }
}
