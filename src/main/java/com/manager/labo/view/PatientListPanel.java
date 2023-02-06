package com.manager.labo.view;

import com.manager.labo.model.PatientModel;
import com.manager.labo.view.components.LaboTableModel;
import com.manager.labo.view.components.TableModelName;

public class PatientListPanel extends AbstractListPanel<PatientModel> {

    public PatientListPanel() {
        super(
            new LaboTableModel<>(TableModelName.PATIENTS, "PESEL", "Nazwisko", "Imię", "Adres", "Telefon"),
            "Patient-",
            "<html>Utwórz badanie dla pacjenta</html>");
    }

}
