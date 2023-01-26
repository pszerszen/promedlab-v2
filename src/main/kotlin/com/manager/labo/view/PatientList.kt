package com.manager.labo.view

import com.manager.labo.model.PatientModel
import com.manager.labo.view.components.LaboTableModel
import com.manager.labo.view.components.TableModelName

class PatientList : ListPanel<PatientModel>() {

    override fun initTableModel() {
        tableModel = LaboTableModel(TableModelName.PATIENTS, "PESEL", "Nazwisko", "Imię", "Adres", "Telefon")
    }

    override val typePrefix: String get() = "Patient-"
    override val actionButtonText: String get() = "<html>Utwórz badanie dla pacjenta</html>"

}
