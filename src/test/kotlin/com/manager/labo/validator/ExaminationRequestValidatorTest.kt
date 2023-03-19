package com.manager.labo.validator

import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.junit5.MockKExtension
import org.apache.commons.collections4.CollectionUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@ExtendWith(MockKExtension::class)
class ExaminationRequestValidatorTest {

    private var model: ExaminationRequestModel = ExaminationRequestModel()

    private val tested = ExaminationRequestValidator()

    @BeforeEach
    fun setUp() {
        model = ExaminationRequestModel(
                examinationId = 0L,
                firstName = "name",
                lastName = "surname",
                pesel = "91010101234",
                birthDay = "1991-08-02",
                address1 = "address1",
                address2 = "address2",
                zipCode = "01-234",
                city = "city",
                phone = "123456789",
                examinations = mutableListOf(
                        ExaminationSummaryModel(code = "A01", description = "description A1"),
                        ExaminationSummaryModel(code = "A02", description = "description A2"),
                        ExaminationSummaryModel(code = "A03", description = "description A3"),
                        ExaminationSummaryModel(code = "B01", description = "description B1"),
                        ExaminationSummaryModel(code = "B02", description = "description B2"),
                        ExaminationSummaryModel(code = "B03", description = "description B3"),
                        ExaminationSummaryModel(code = "C01", description = "description C1"),
                        ExaminationSummaryModel(code = "C02", description = "description C2"),
                        ExaminationSummaryModel(code = "C03", description = "description C3")
                )
        )
    }

    @Test
    fun `should pass valid model`() {
        val errors: Set<String> = tested.validate(model, false)

        assertValidationErrors(errors)
    }

    @Test
    fun `should fail for invalid ExaminationSummaryModels`() {
        val errors: Set<String> = tested.validate(model, true)

        errors shouldHaveSize 2
        errors shouldContain "Nie wpisano wykonującego badania."
        errors shouldContain "Należy uzupełnić wartość badania."
        assertValidationErrors(errors,
                               "Nie wpisano wykonującego badania.",
                               "Należy uzupełnić wartość badania.")
    }

    private fun assertValidationErrors(validationErrors: Collection<String>, vararg expectedValidationErrors: String) {
        val expectedErrors = listOf(*expectedValidationErrors)
        val equalCollection = CollectionUtils.isEqualCollection(validationErrors, expectedErrors)
        if (!equalCollection) {
            CollectionUtils.subtract(validationErrors, expectedErrors)
                .forEach { log.error("Unexpected error: $it") }
            CollectionUtils.subtract(expectedErrors, validationErrors)
                .forEach { log.error("Expected error not hit: $it") }
        }
        equalCollection shouldBe true
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(ExaminationRequestValidatorTest::class.java)
    }
}
