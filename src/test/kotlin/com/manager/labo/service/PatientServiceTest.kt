package com.manager.labo.service

import com.manager.labo.entities.Patient
import com.manager.labo.mapper.PatientMapper
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.PatientModel
import com.manager.labo.repository.PatientRepository
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull

@ExtendWith(MockKExtension::class)
class PatientServiceTest {

    private val PESEL = "1234567890"

    @MockK
    lateinit var patientRepository: PatientRepository

    @MockK
    lateinit var patientMapper: PatientMapper

    @InjectMockKs
    lateinit var tested: PatientService

    @MockK
    lateinit var patient: Patient
    @MockK
    lateinit var model: PatientModel

    @Test
    fun getPatientModelByPesel() {
        every { patientRepository.getByPesel(PESEL) } returns patient
        every { patientMapper.toPatientModel(patient) } returns model

        tested.getPatientModelByPesel(PESEL) shouldBe model
    }

    @Test
    fun getById() {
        val id = 1L
        every { patientRepository.findByIdOrNull(id) } returns patient
        every { patientMapper.toPatientModel(patient) } returns model

        tested.getById(id) shouldBe model
    }

    @Test
    fun getAll() {
        every { patientRepository.findAll() } returns listOf(patient)
        every { patientMapper.toPatientModel(patient) } returns model

        tested.all.let {
            it shouldHaveSize 1
            it shouldContain model
        }
    }

    @Test
    fun `should create new patient when it's new`(@MockK model: ExaminationRequestModel) {
        every { model.pesel } returns PESEL
        every { patientRepository.getByPesel(PESEL) } returns null
        every { patientMapper.fromExaminationRequestModel(model) } returns patient
        every { patientRepository.save(patient) } returnsArgument 0

        tested.fromExaminationRequest(model) shouldBe patient
        verify { patientMapper.fromExaminationRequestModel(model) }
        verify { patientMapper.updateFromExaminationRequestMapper(any(), any()) wasNot called }
    }

    @Test
    fun `should update patient when it's present`(@MockK model: ExaminationRequestModel) {
        every { model.pesel } returns PESEL
        every { patientRepository.getByPesel(PESEL) } returns patient
        every { patientMapper.updateFromExaminationRequestMapper(patient, model) } returns patient
        every { patientRepository.save(patient) } returnsArgument 0

        tested.fromExaminationRequest(model) shouldBe patient

        verify { patientMapper.fromExaminationRequestModel(any()) wasNot called }
        verify { patientMapper.updateFromExaminationRequestMapper(any(), any()) }
    }
}
