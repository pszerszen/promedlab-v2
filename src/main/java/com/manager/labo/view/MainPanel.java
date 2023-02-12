package com.manager.labo.view;

import com.manager.labo.utils.ActionCommand;
import com.manager.labo.view.components.JPanelEnchancer;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.springframework.stereotype.Component;

@Component
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
        postCreateUIComponents();
    }

    private void createUIComponents() {
        mainPanel = new JPanel();
        newRequest = new JButton();
        patientsLists = new JButton();
        examinationList = new JButton();
    }

    private void postCreateUIComponents() {
        add(mainPanel);
        setSize(mainPanel.getPreferredSize());
        new JPanelEnchancer(this).standardActions().initButtonsActionCommands();
    }

}
