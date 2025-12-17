package com.challenge

import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import tools.jackson.module.kotlin.KotlinInvalidNullException
import java.time.Instant

@RestControllerAdvice
class ControllerExceptionHandler {

    private fun buildProblemDetail(
        status: HttpStatus,
        title: String,
        detail: String,
        fieldErrors: Map<String, String>? = null,
        globalErrors: Map<String, String>? = null
    ): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(status, detail)
        problemDetail.title = title
        fieldErrors?.let { problemDetail.setProperty("fields", it) }
        globalErrors?.let { problemDetail.setProperty("global", it) }
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(): ProblemDetail {
        return buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Data Integrity Error",
            "A constraint violation was detected in the data. "
        )
    }

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(ex: DomainException): ProblemDetail {
        return buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Domain Error",
            ex.message ?: "An error occurred"
        )
    }

    @ExceptionHandler(FieldValidationException::class)
    fun handleFieldValidationException(ex: FieldValidationException): ProblemDetail {
        return buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Field Validation Error",
            "Validation failed. Check 'errors' for details",
            mapOf(ex.field to ex.message!!)
        )
    }

    /**
     * It covers validation done from Spring MVC (Data Binder)
     */
    @ExceptionHandler(value = [BindException::class, MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(ex: BindException): ProblemDetail {
        val fieldErrors = mutableMapOf<String, String>()
        val globalErrors = mutableMapOf<String, String>()
        ex.bindingResult.globalErrors.forEach { globalError ->
            globalErrors[globalError.objectName] = globalError.defaultMessage ?: "Validation error"
        }
        ex.bindingResult.fieldErrors.forEach { fieldError ->
            fieldErrors[fieldError.field] = fieldError.defaultMessage ?: "Validation error"
        }
        return buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Validation Error",
            "Validation failed. Check 'errors' for details",
            fieldErrors,
            globalErrors
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ProblemDetail {
        return buildProblemDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error",
            "An error occurred. Please try again later"
        )
    }
    /**
     * It covers validation done from Hibernate
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ProblemDetail {
        val errors = mutableMapOf<String, String>()
        ex.constraintViolations.forEach { violation ->
            errors[violation.propertyPath.toString()] = violation.message
        }
        return buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Validation Error",
            "Validation failed. Check 'errors' for details",
            errors
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ProblemDetail {
        if (ex.cause is KotlinInvalidNullException) {
            val problem = ex.cause as KotlinInvalidNullException
            return buildProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                "Payload is invalid",
                mapOf(problem.propertyName.toString() to "Field is required")
            )
        }

        return buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Payload is invalid",
            "The payload is invalid. Please send a valid JSON"
        )
    }
}