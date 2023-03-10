package com.manager.labo.view;

import com.manager.labo.model.ExaminationModel;
import com.manager.labo.view.components.LaboTableModel;
import com.manager.labo.view.components.TableModel;

public final class ExaminationListPanel extends AbstractListPanel<ExaminationModel> {

    public ExaminationListPanel() {
        super(
            new LaboTableModel<>(TableModel.REQUESTS),
            "Examination-",
            "Szczegóły/ Wprowadź wyniki badań");
    }
}
