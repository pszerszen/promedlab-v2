package com.manager.labo.model

import com.manager.labo.utils.MappingField
import com.manager.labo.utils.ValidDate
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class ExaminationRequestModel {
    var examinationId: Long? = null

    @MappingField
    @NotNull(message = BRAK_IMIENIA)
    var firstName: String? = null

    @MappingField
    @NotNull(message = BRAK_NAZWISKA)
    var lastName: String? = null

    @MappingField
    @Pattern(regexp = "(\\d{11})?", message = NIEPRAWIDŁOWY_FORMAT_NR_PESEL)
    var pesel: String? = null

    @MappingField
    @ValidDate(message = NIEPRAWIDŁOWA_DATA)
    @NotNull(message = BRAK_DATY_URODZENIA)
    @Pattern(regexp = "\\d{4}(-\\d{2}){2}", message = NIEPRAWIDŁOWY_ZAPIS_DATY)
    var birthDay: String? = null

    @MappingField
    @NotNull(message = NIE_WPROWADZONO_ADRESU)
    var address1: String? = null

    @MappingField
    var address2: String? = null

    @MappingField
    @NotNull(message = BRAK_KODU_POCZTOWEGO)
    @Pattern(regexp = "\\d{2}-\\d{3}", message = ZŁY_FORMAT_KODU_POCZTOWEGO)
    var zipCode: String? = null

    @MappingField
    @NotNull(message = BRAK_MIASTA)
    var city: String? = null

    @MappingField
    @NotNull(message = BRAK_NR_TELEFONU)
    @Pattern(regexp = "(\\(\\+\\d{2}\\))* ?\\d{9}", message = ZŁY_FORMAT_TELEFONU)
    var phone: String? = null

    @Valid
    var examinations: MutableList<ExaminationSummaryModel> = mutableListOf()

    constructor()

    constructor(
        examinationId: Long?,
        firstName: String?,
        lastName: String?,
        pesel: String?,
        birthDay: String?,
        address1: String?,
        address2: String?,
        zipCode: String?,
        city: String?,
        phone: String?,
        examinations: MutableList<ExaminationSummaryModel>
    ) {
        this.examinationId = examinationId
        this.firstName = firstName
        this.lastName = lastName
        this.pesel = pesel
        this.birthDay = birthDay
        this.address1 = address1
        this.address2 = address2
        this.zipCode = zipCode
        this.city = city
        this.phone = phone
        this.examinations = examinations
    }

    fun addExamination(examinationSummaryModel: ExaminationSummaryModel) {
        examinations.add(examinationSummaryModel)
    }

    companion object {
        private const val ZŁY_FORMAT_TELEFONU = "Zły format telefonu."
        private const val ZŁY_FORMAT_KODU_POCZTOWEGO = "Zły format kodu pocztowego"
        private const val BRAK_NR_TELEFONU = "Brak nr telefonu"
        private const val BRAK_MIASTA = "Brak miasta."
        private const val BRAK_KODU_POCZTOWEGO = "Brak kodu pocztowego."
        private const val NIE_WPROWADZONO_ADRESU = "Nie wprowadzono adresu."
        private const val NIEPRAWIDŁOWA_DATA = "Nieprawidłowa data."
        private const val NIEPRAWIDŁOWY_ZAPIS_DATY = "Nieprawidłowy zapis daty (wymagany format: rrrr-MM-dd)"
        private const val BRAK_DATY_URODZENIA = "Brak daty urodzenia."
        private const val NIEPRAWIDŁOWY_FORMAT_NR_PESEL = "Nieprawidłowy format nr PESEL."
        private const val BRAK_NAZWISKA = "Brak nazwiska."
        private const val BRAK_IMIENIA = "Brak imienia."
    }
}
