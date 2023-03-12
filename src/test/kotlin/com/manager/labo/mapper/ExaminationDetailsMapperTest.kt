package com.manager.labo.mapper

import com.manager.labo.entities.Examination
import com.manager.labo.entities.ExaminationDetails
import com.manager.labo.model.ExaminationSummaryModel
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class ExaminationDetailsMapperTest {

    val tested = ExaminationDetailsMapper()

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun fromExaminationSummaryModel(@MockK examination: Examination) {
        val examinationSupplier = { examination }
        val model = ExaminationSummaryModel(code = "code", description = null)

        val actual = tested.fromExaminationSummaryModel(model, examinationSupplier)

        actual.code shouldBe model.code
        actual.examination shouldBe examination
        actual.date shouldBeLessThanOrEqualTo LocalDateTime.now()
    }

    @Test
    fun updateFromExaminationSummaryModel(@MockK examination: Examination) {
        val details = ExaminationDetails(
            id = 1L,
            examination = examination,
            code = "code",
            value = null,
            staffName = null,
            date = LocalDateTime.now()
        )
        val model = ExaminationSummaryModel(
            id = null,
            code = null,
            description = null,
            staffName = "staffName",
            value = 12
        )

        val actual = tested.updateFromExaminationSummaryModel(details, model)

        actual.id shouldBe details.id
        actual.examination shouldBe examination
        actual.code shouldBe details.code
        actual.date shouldBe details.date
        actual.staffName shouldBe model.staffName
        actual.value shouldBe model.value
    }

    @Test
    fun toExaminationSummaryModel(@MockK examination: Examination, @MockK descriptionSupplier: (code: String) -> String) {
        val examinationDetails = ExaminationDetails(
            id = 1L,
            examination = examination,
            code = "code",
            value = 12,
            staffName = "staffName",
            date = LocalDateTime.now()
        )
        every { descriptionSupplier.invoke(examinationDetails.code) } returns "description"

        val actual = tested.toExaminationSummaryModel(examinationDetails, descriptionSupplier)

        actual.id shouldBe examinationDetails.id
        actual.code shouldBe examinationDetails.code
        actual.description shouldBe "description"
        actual.staffName shouldBe examinationDetails.staffName
        actual.value shouldBe examinationDetails.value
    }
}
