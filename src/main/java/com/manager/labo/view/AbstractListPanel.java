package com.manager.labo.view;

import com.manager.labo.view.components.JPanelEnchancer;
import com.manager.labo.view.components.LaboTableModel;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public abstract class AbstractListPanel<T> extends JPanel {

    private static final String SEE = "See";
    private static final String RELOAD = "Reload";

    private final LaboTableModel<T> tableModel;
    private final String typePrefix;
    private final String actionButtonText;

    private JTable table;
    private JPanel mainPanel;
    private JButton back;
    private JButton action;
    private JButton reload;

    protected AbstractListPanel(LaboTableModel<T> tableModel, String typePrefix, String actionButtonText) {
        super();
        this.tableModel = tableModel;
        this.typePrefix = typePrefix;
        this.actionButtonText = actionButtonText;

        postCreateUIComponents();
        new JPanelEnchancer(this).standardActions();
    }

    private void createUIComponents() {
        action = new JButton(actionButtonText);
        action.setActionCommand(typePrefix + SEE);

        table = new JTable();
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setSelectionMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void postCreateUIComponents() {
        reload.setActionCommand(typePrefix + RELOAD);
        add(mainPanel);
        setSize(mainPanel.getSize());
    }

    public T getCurrentModel() {
        return tableModel.getRowAsModel(table.getSelectedRow());
    }

    public void reloadTable(List<T> models) {
        tableModel.setRowCount(0);
        tableModel.addRows(models);
    }
}
