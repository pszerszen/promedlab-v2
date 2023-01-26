package com.manager.labo.view

import com.manager.labo.model.ExaminationModel
import com.manager.labo.view.components.LaboTableModel
import com.manager.labo.view.components.TableModelName

class ExaminationList(
    override val typePrefix: String = "Examination-",
    override val actionButtonText: String = "Szczegóły/Wprowadź wyniki badań"
) : ListPanel<ExaminationModel>() {
    override fun initTableModel() {
        tableModel = LaboTableModel(TableModelName.REQUESTS, "Data Zlecenia", "Kod badania", "PESEL", "Nazwisko", "Imię", "Adres", "Telefon")
    }
}
