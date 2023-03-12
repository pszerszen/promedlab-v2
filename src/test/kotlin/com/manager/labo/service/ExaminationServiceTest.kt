package com.manager.labo.service

import com.manager.labo.entities.Examination
import com.manager.labo.mapper.ExaminationDetailsMapper
import com.manager.labo.mapper.ExaminationMapper
import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import com.manager.labo.repository.ExaminationDetailsRepository
import com.manager.labo.repository.ExaminationRepository
import com.manager.labo.repository.PatientRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.apache.commons.collections4.CollectionUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.function.Consumer

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

        model.address1 = "address1"
        model.address2 = "address2"
        model.city = "city"
        model.examinationId = 0L
        model.firstName = "name"
        model.lastName = "surname"
        model.pesel = "91080208596"
        model.birthDay = "1991-08-02"
        model.phone = "798749030"
        model.zipCode = "20-570"
        model.addExamination(ExaminationSummaryModel("A", "description A1"))
        model.addExamination(ExaminationSummaryModel("A", "description A2"))
        model.addExamination(ExaminationSummaryModel("A", "description A3"))
        model.addExamination(ExaminationSummaryModel("B", "description B1"))
        model.addExamination(ExaminationSummaryModel("B", "description B2"))
        model.addExamination(ExaminationSummaryModel("B", "description B3"))
        model.addExamination(ExaminationSummaryModel("C", "description C1"))
        model.addExamination(ExaminationSummaryModel("C", "description C2"))
        model.addExamination(ExaminationSummaryModel("C", "description C3"))
    }

    @Test
    fun all(@MockK examination: Examination, ) {

    }

    private fun assertValidationErrors(validationErrors: Collection<String?>, vararg expectedValidationErrors: String) {
        val expectedErrors: Collection<String?> = Arrays.asList(*expectedValidationErrors)
        val equalCollection = CollectionUtils.isEqualCollection(validationErrors, expectedErrors)
        if (!equalCollection) {
            CollectionUtils.subtract(validationErrors, expectedErrors)
                .forEach(Consumer { error: String? -> log.error("Unexpected error: $error") })
            CollectionUtils.subtract(expectedErrors, validationErrors)
                .forEach(Consumer { error: String? -> log.error("Expected error not hit: $error") })
        }
        assertTrue(equalCollection)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ExaminationServiceTest::class.java)
    }

}
