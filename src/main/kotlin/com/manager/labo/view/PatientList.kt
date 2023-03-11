package com.manager.labo.view

import com.manager.labo.model.PatientModel
import com.manager.labo.view.components.LaboTableModel
import com.manager.labo.view.components.TableModel

@Deprecated(message = "Use java swing components", replaceWith = ReplaceWith("com.manager.labo.view.PatientListPanel"))
class PatientList : ListPanel<PatientModel>() {

    override fun initTableModel() {
        tableModel = LaboTableModel(TableModel.PATIENTS)
    }

    override val typePrefix: String get() = "Patient-"
    override val actionButtonText: String get() = "<html>Utwórz badanie dla pacjenta</html>"

}
