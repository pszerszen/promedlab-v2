package com.manager.labo.mapper

import com.manager.labo.entities.Examination
import com.manager.labo.entities.Patient
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.shouldBe
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class ExaminationMapperTest {

    private val tested = ExaminationMapper()

    private lateinit var patient: Patient
    private lateinit var examination: Examination

    @BeforeEach
    fun setUp() {
        patient = Patient(
                id = 1L,
                firstName = "firstName",
                lastName = "lastName",
                pesel = "12345678901",
                address1 = "address1",
                address2 = "address2",
                city = "city",
                zipCode = "01-234",
                phone = "123456789",
                birth = LocalDate.of(1991, 1, 1)
        )
        examination = Examination(
                id = 1L,
                patient = patient,
                date = LocalDateTime.of(2023, 3, 12, 12, 0),
                code = "code"
        )
    }

    @Nested
    inner class ToModel {

        @Test
        fun toExaminationModel() =
            with(tested.toExaminationModel(examination)) {
                id shouldBe examination.id
                requestDate shouldBe "2023-03-12 12:00:00"
                code shouldBe examination.code
                pesel shouldBe patient.pesel
                lastName shouldBe patient.lastName
                firstName shouldBe patient.firstName
                address shouldBe "${patient.address1} ${patient.address2}"
                phone shouldBe patient.phone
            }

        @Test
        fun toExaminationRequestModel(@MockK summaryModel: ExaminationSummaryModel) =
            with(tested.toExaminationRequestModel(examination) { mutableListOf(summaryModel) }) {
                examinationId shouldBe examination.id
                firstName shouldBe patient.firstName
                lastName shouldBe patient.lastName
                pesel shouldBe patient.pesel
                birthDay shouldBe "1991-01-01"
                address1 shouldBe patient.address1
                address2 shouldBe patient.address2
                zipCode shouldBe patient.zipCode
                city shouldBe patient.city
                phone shouldBe patient.phone
                examinations shouldHaveSingleElement summaryModel
            }
    }

    @Nested
    inner class FromModel {

        private lateinit var model: ExaminationRequestModel

        @BeforeEach
        fun setUp() {
            model = ExaminationRequestModel()
            model.examinations.add(ExaminationSummaryModel("A01", "Badanie ogólne moczu (profil)"))
        }


        @Test
        fun fromExaminationRequestModel(@MockK patient: Patient) =
            with(tested.fromExaminationRequestModel(model) { patient }) {
                id shouldBe null
                patient shouldBe patient
                date shouldBeLessThanOrEqualTo LocalDateTime.now()
                code shouldBe "A"
                examinationDetails shouldHaveSize 0
            }

        @Test
        fun updateFromExaminationRequestModel() =
            with(tested.updateFromExaminationRequestModel(examination, model)) {
                id shouldBe examination.id
                patient shouldBe examination.patient
                date shouldBeLessThanOrEqualTo LocalDateTime.now()
                code shouldBe "A"
                examinationDetails shouldBe examination.examinationDetails
            }
    }
}
