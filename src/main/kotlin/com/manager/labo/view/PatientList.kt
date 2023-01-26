package com.manager.labo.view

import com.manager.labo.model.PatientModel
import com.manager.labo.view.components.LaboTableModel
import com.manager.labo.view.components.TableModelName

class PatientList(
    override val typePrefix: String = "Patient-",
    override val actionButtonText: String = "Utwórz badanie dla pacjenta"
) : ListPanel<PatientModel>() {

    override fun initTableModel() {
        tableModel = LaboTableModel(TableModelName.PATIENTS, "PESEL", "Nazwisko", "Imię", "Adres", "Telefon")
    }

}
