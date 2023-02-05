package com.manager.labo.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.manager.labo.utils.ActionCommand;
import com.manager.labo.view.components.JPanelEnchancer;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

    @ActionCommand("Examination-Add")
    private JButton newRequest;
    @ActionCommand("Patient-List")
    private JButton patientsLists;
    @ActionCommand("Examination-List")
    private JButton examinationList;
    private JPanel panel;

    public MainPanel() {
        super();
        $$$setupUI$$$();
        add(panel);
        setSize(panel.getSize());
        new JPanelEnchancer(this).standardActions().initButtonsActionCommands();
    }

    private void createUIComponents() {
        panel = new JPanel();
        newRequest = new JButton();
        patientsLists = new JButton();
        examinationList = new JButton();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 10, 0, 10), -1, -1));
        panel.setMaximumSize(new Dimension(440, 115));
        panel.setMinimumSize(new Dimension(440, 115));
        panel.setPreferredSize(new Dimension(440, 115));
        newRequest.setText("Nowe zlecenie");
        panel.add(newRequest, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                                                  GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                  GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        patientsLists.setText("Lista Pacjentów");
        panel.add(patientsLists, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                                                     GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                     GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        examinationList.setText("Lista Badań");
        panel.add(examinationList, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                                                       GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                                                       GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
