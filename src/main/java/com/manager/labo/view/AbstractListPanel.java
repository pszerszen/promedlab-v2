package com.manager.labo.view;

import com.manager.labo.view.components.JPanelEnchancer;
import com.manager.labo.view.components.LaboTableModel;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    }

    private void createUIComponents() {
        mainPanel = new JPanel();

        action = new JButton(actionButtonText);
        action.setActionCommand(typePrefix + SEE);

        table = new JTable();
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        reload = new JButton();
        reload.setActionCommand(typePrefix + RELOAD);
    }

    private void postCreateUIComponents() {
        add(mainPanel);
        setSize(mainPanel.getPreferredSize());
        new JPanelEnchancer(this).standardActions();
    }

    @Nullable
    public T getCurrentModel() {
        return tableModel.getRowAsModel(table.getSelectedRow());
    }

    public void reloadTable(@NotNull List<? extends T> models) {
        tableModel.setRowCount(0);
        tableModel.addRows(models);
    }
}
