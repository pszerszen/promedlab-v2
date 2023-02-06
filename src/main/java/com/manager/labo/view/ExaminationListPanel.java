package com.manager.labo.view;

import com.manager.labo.model.ExaminationModel;
import com.manager.labo.view.components.LaboTableModel;
import com.manager.labo.view.components.TableModelName;

public class ExaminationListPanel extends AbstractListPanel<ExaminationModel> {

    public ExaminationListPanel() {
        super(
            new LaboTableModel<>(TableModelName.REQUESTS, "Data Zlecenia", "Kod badania", "PESEL", "Nazwisko", "Imię", "Adres", "Telefon"),
            "Examination-",
            "<html>Szczegóły/ Wprowadź wyniki badań</html>");
    }
}
