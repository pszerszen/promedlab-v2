package com.manager.labo.service

import com.manager.labo.entities.Examination
import com.manager.labo.mapper.ExaminationDetailsMapper
import com.manager.labo.mapper.ExaminationMapper
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.repository.ExaminationDetailsRepository
import com.manager.labo.repository.ExaminationRepository
import com.manager.labo.repository.PatientRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@ExtendWith(MockKExtension::class)
class ExaminationServiceTest {

    private var model: ExaminationRequestModel = ExaminationRequestModel()

    @MockK
    lateinit var examinationRepository: ExaminationRepository
    @MockK
    lateinit var examinationDetailsRepository: ExaminationDetailsRepository
    @MockK
    lateinit var patientRepository: PatientRepository
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

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun all(@MockK examination: Examination) {

    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ExaminationServiceTest::class.java)
    }

}
