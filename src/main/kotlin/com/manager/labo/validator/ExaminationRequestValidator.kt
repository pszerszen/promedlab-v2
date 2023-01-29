package com.manager.labo.validator

import com.manager.labo.model.ExaminationRequestModel
import com.manager.labo.model.ExaminationSummaryModel
import com.manager.labo.utils.ValidDate
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.reflect.Field
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@Component
class ExaminationRequestValidator {

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    fun validate(model: ExaminationRequestModel, validateExamiations: Boolean): Set<String> {
        val errors: MutableSet<String> = mutableSetOf()
        for (field in model.javaClass.declaredFields) {
            extractAndValidateField(field, model, errors)
        }
        val examinations: List<ExaminationSummaryModel?> = model.examinations
        if (CollectionUtils.isEmpty(examinations)) {
            errors.add("Należy wybrać przynajmniej jedno badanie.")
        }
        if (validateExamiations) {
            examinations.forEach { examination ->
                for (field in examination!!.javaClass.declaredFields) {
                    try {
                        extractAndValidateField(field, examination, errors)
                    } catch (e: Exception) {
                        log.error("Error while validating.", e)
                    }
                }
            }
        }
        return errors
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    private fun extractAndValidateField(field: Field, `object`: Any, errors: MutableSet<String>) {
        field.isAccessible = true
        val objectVal = field[`object`]
        val fieldValue = objectVal?.toString() ?: ""
        val error = validateField(field, fieldValue)
        if (error != null) {
            errors.add(error)
        }
    }

    private fun validateField(field: Field, fieldValue: String): String? {
        val notNullAnno: NotNull? = field.getAnnotation(NotNull::class.java)
        if (notNullAnno != null && StringUtils.isBlank(fieldValue)) {
            return notNullAnno.message
        }
        val patternAnno: Pattern? = field.getAnnotation(Pattern::class.java)
        if (patternAnno != null && !fieldValue.matches(patternAnno.regexp.toRegex())) {
            return patternAnno.message
        }
        val validDateAnno: ValidDate? = field.getAnnotation(ValidDate::class.java)
        if (validDateAnno != null) {
            try {
                LocalDate.parse(fieldValue, DateTimeFormatter.ofPattern(validDateAnno.dateFormat))
            } catch (e: DateTimeParseException) {
                return validDateAnno.message
            }
        }
        return null
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ExaminationRequestValidator::class.java)
    }
}
