package com.manager.labo.view;

import static com.manager.labo.utils.ActionCommandsKt.ADD_TO_EXAMINATIONS;
import static com.manager.labo.utils.ActionCommandsKt.EXAMINATION_SUBMIT;
import static com.manager.labo.utils.ActionCommandsKt.EXIT;
import static com.manager.labo.utils.ActionCommandsKt.REMOVE_FROM_EXAMINATIONS;
import static com.manager.labo.utils.ActionCommandsKt.SEARCH_FOR_PATIENT;

import com.manager.labo.model.ExaminationRequestModel;
import com.manager.labo.model.ExaminationSummaryModel;
import com.manager.labo.utils.ActionCommand;
import com.manager.labo.view.components.LaboTableModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

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
    private JFormattedTextField phone;
    private JLabel examinationGroupLbl;
    private JLabel examinationListLbl;
    private JComboBox<String> examinationGroup;
    private JComboBox<String> comboBox2;
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

    public ExaminationDetailsForm(ExaminationRequestModel model) {
        this.model = model;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
