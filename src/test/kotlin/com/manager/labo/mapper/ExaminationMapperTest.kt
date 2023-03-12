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
        fun toExaminationModel() {
            val model = tested.toExaminationModel(examination)

            model.id shouldBe examination.id
            model.requestDate shouldBe "2023-03-12 12:00:00"
            model.code shouldBe examination.code
            model.pesel shouldBe patient.pesel
            model.lastName shouldBe patient.lastName
            model.firstName shouldBe patient.firstName
            model.address shouldBe "${patient.address1} ${patient.address2}"
            model.phone shouldBe patient.phone
        }

        @Test
        fun toExaminationRequestModel(@MockK summaryModel: ExaminationSummaryModel) {
            val examinationDetailsSupplier = { mutableListOf(summaryModel) }
            val model = tested.toExaminationRequestModel(examination, examinationDetailsSupplier)

            model.examinationId shouldBe examination.id
            model.firstName shouldBe patient.firstName
            model.lastName shouldBe patient.lastName
            model.pesel shouldBe patient.pesel
            model.birthDay shouldBe "1991-01-01"
            model.address1 shouldBe patient.address1
            model.address2 shouldBe patient.address2
            model.zipCode shouldBe patient.zipCode
            model.city shouldBe patient.city
            model.phone shouldBe patient.phone
            model.examinations shouldHaveSingleElement summaryModel
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
        fun fromExaminationRequestModel(@MockK patient: Patient) {
            val patientSupplier = { patient }

            val examination = tested.fromExaminationRequestModel(model, patientSupplier)

            examination.id shouldBe null
            examination.patient shouldBe patient
            examination.date shouldBeLessThanOrEqualTo LocalDateTime.now()
            examination.code shouldBe "A"
            examination.examinationDetails shouldHaveSize 0
        }

        @Test
        fun updateFromExaminationRequestModel() {
            val (id,
                examinationPatient,
                date,
                code,
                examinationDetails) = tested.updateFromExaminationRequestModel(examination, model)

            id shouldBe examination.id
            examinationPatient shouldBe examination.patient
            date shouldBeLessThanOrEqualTo LocalDateTime.now()
            code shouldBe "A"
            examinationDetails shouldBe examination.examinationDetails
        }
    }
}
