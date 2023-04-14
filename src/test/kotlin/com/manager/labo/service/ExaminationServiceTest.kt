package com.manager.labo.service

import com.manager.labo.entities.Examination
import com.manager.labo.entities.ExaminationDetails
import com.manager.labo.mapper.ExaminationDetailsMapper
import com.manager.labo.mapper.ExaminationMapper
import com.manager.labo.model.ExaminationModel
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.repository.ExaminationDetailsRepository
import com.manager.labo.repository.ExaminationRepository
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@ExtendWith(MockKExtension::class)
class ExaminationServiceTest {

    private val EXAMINATION_ID = 1L

    private var model = ExaminationRequestModel()

    @MockK
    lateinit var examinationRepository: ExaminationRepository
    @MockK
    lateinit var examinationDetailsRepository: ExaminationDetailsRepository
    @MockK
    lateinit var examinationDetailsMapper: ExaminationDetailsMapper
    @MockK
    lateinit var examinationMapper: ExaminationMapper
    @MockK
    lateinit var patientService: PatientService
    @MockK
    lateinit var icdService: IcdService

    @InjectMockKs
    lateinit var tested: ExaminationService


    @Test
    fun all(@MockK examination: Examination, @MockK examinationModel: ExaminationModel) {
        every { examinationRepository.findAll() } returns listOf(examination)
        every { examinationMapper.toExaminationModel(examination) } returns examinationModel

        tested.all.let {
            it shouldHaveSize 1
            it shouldContain examinationModel
        }
    }

    @Test
    fun `should map to ExaminationRequestModel`(@MockK details: ExaminationDetails,
                                                @MockK examination: Examination) {
        every { examination.examinationDetails } returns mutableSetOf(details)
        every { examinationRepository.getReferenceById(EXAMINATION_ID) } returns examination
        every { examinationMapper.toExaminationRequestModel(eq(examination), any())  } returns model

        tested.getExaminationRequestModel(EXAMINATION_ID) shouldBe model
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ExaminationServiceTest::class.java)
    }

}
