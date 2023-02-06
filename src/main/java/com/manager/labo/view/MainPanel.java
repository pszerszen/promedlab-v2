package com.manager.labo.view;

import com.manager.labo.utils.ActionCommand;
import com.manager.labo.view.components.JPanelEnchancer;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

    @ActionCommand("Examination-Add")
    private JButton newRequest;
    @ActionCommand("Patient-List")
    private JButton patientsLists;
    @ActionCommand("Examination-List")
    private JButton examinationList;
    private JPanel mainPanel;

    public MainPanel() {
        super();
        add(mainPanel);
        setSize(mainPanel.getSize());
        new JPanelEnchancer(this).standardActions().initButtonsActionCommands();
    }

    private void createUIComponents() {
        mainPanel = new JPanel();
        newRequest = new JButton();
        patientsLists = new JButton();
        examinationList = new JButton();
    }

}
