package com.manager.labo.mapper

import com.manager.labo.entities.Patient
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.PatientModel
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PatientMapperTest {

    private val tested = PatientMapper()

    private lateinit var patient: Patient
    private lateinit var model: ExaminationRequestModel

    @BeforeEach
    fun setUp() {
        patient = Patient(id = 1L,
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

        with(patient) {
            model = ExaminationRequestModel(examinationId = 1L,
                                            firstName = firstName,
                                            lastName = lastName,
                                            pesel = pesel,
                                            birthDay = "1991-01-01",
                                            address1 = address1,
                                            address2 = address2,
                                            zipCode = zipCode,
                                            city = city,
                                            phone = phone,
                                            examinations = mutableListOf()
            )
        }
    }

    @Test
    fun `should toPatientModel return null when null provided in parameter`() =
        tested.toPatientModel(null) shouldBe null

    @Test
    fun toPatientModel() {
        val patientModel = tested.toPatientModel(patient)

        patientModel shouldNotBe null

        patientModel!!.id shouldBe patient.id
        patientModel.pesel shouldBe patient.pesel
        patientModel.birthDay shouldBe "1991-01-01"
        patientModel.lastName shouldBe patient.lastName
        patientModel.firstName shouldBe patient.firstName
        patientModel.address1 shouldBe patient.address1
        patientModel.address2 shouldBe patient.address2
        patientModel.phone shouldBe patient.phone
        patientModel.zipCode shouldBe patient.zipCode
        patientModel.city shouldBe patient.city
    }

    @Test
    fun fromPatientModel() {
        val patientModel = PatientModel(id = patient.id,
                                        pesel = patient.pesel,
                                        birthDay = "1991-01-01",
                                        lastName = patient.lastName,
                                        firstName = patient.firstName,
                                        address1 = patient.address1,
                                        address2 = patient.address2,
                                        phone = patient.phone,
                                        zipCode = patient.zipCode,
                                        city = patient.city
        )

        tested.fromPatientModel(patientModel) shouldBeEqualToComparingFields patient
    }

    @Test
    fun fromExaminationRequestModel() {
        val (id,
            firstName,
            lastName,
            pesel,
            address1,
            address2,
            city,
            zipCode,
            phone,
            birth,
            examinations) = tested.fromExaminationRequestModel(model)

        id shouldBe null
        firstName shouldBe model.firstName
        lastName shouldBe model.lastName
        pesel shouldBe model.pesel
        address1 shouldBe model.address1
        address2 shouldBe model.address2
        city shouldBe model.city
        zipCode shouldBe model.zipCode
        phone shouldBe model.phone
        birth shouldBe patient.birth
        examinations shouldHaveSize 0
    }

    @Test
    fun updateFromExaminationRequestMapper() {
        val (id,
            firstName,
            lastName,
            pesel,
            address1,
            address2,
            city,
            zipCode,
            phone,
            birth,
            examinations) = tested.updateFromExaminationRequestMapper(patient, model)

        id shouldBe patient.id
        firstName shouldBe model.firstName
        lastName shouldBe model.lastName
        pesel shouldBe model.pesel
        address1 shouldBe model.address1
        address2 shouldBe model.address2
        city shouldBe model.city
        zipCode shouldBe model.zipCode
        phone shouldBe patient.phone
        birth shouldBe patient.birth
    }
}
