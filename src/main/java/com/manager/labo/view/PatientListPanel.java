package com.manager.labo.view;

import com.manager.labo.model.PatientModel;
import com.manager.labo.view.components.LaboTableModel;
import com.manager.labo.view.components.TableModel;

public class PatientListPanel extends AbstractListPanel<PatientModel> {

    public PatientListPanel() {
        super(
            new LaboTableModel<>(TableModel.PATIENTS),
            "Patient-",
            "<html>Utwórz badanie dla pacjenta</html>");
    }

}
