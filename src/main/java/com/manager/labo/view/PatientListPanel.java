package com.manager.labo.view;

import com.manager.labo.model.PatientModel;
import com.manager.labo.view.components.LaboTableModel;
import com.manager.labo.view.components.TableModelName;

public class PatientListPanel extends AbstractListPanel<PatientModel> {

    protected PatientListPanel(LaboTableModel<PatientModel> tableModel, String typePrefix, String actionButtonText) {
        super(
            new LaboTableModel<>(TableModelName.PATIENTS, "PESEL", "Nazwisko", "Imię", "Adres", "Telefon"),
            "Patient-",
            "<html>Utwórz badanie dla pacjenta</html>");
    }

}
