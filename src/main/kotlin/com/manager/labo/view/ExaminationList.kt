package com.manager.labo.view

import com.manager.labo.model.ExaminationModel
import com.manager.labo.view.components.LaboTableModel
import com.manager.labo.view.components.TableModel

@Deprecated(message = "Use java swing components", replaceWith = ReplaceWith("com.manager.labo.view.ExaminationListPanel"))
class ExaminationList : ListPanel<ExaminationModel>() {

    override fun initTableModel() {
        tableModel = LaboTableModel(TableModel.REQUESTS)
    }

    override val typePrefix: String
        get() = "Examination-"
    override val actionButtonText: String
        get() = "<html>Szczegóły/ Wprowadź wyniki badań</html>"
}
