package com.challenge

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Validation failed. Check 'errors' for details"
        )
        problemDetail.title = "Validation Error"
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.fieldErrors.forEach { fieldError ->
            errors[fieldError.field] = fieldError.defaultMessage ?: "Validation error"
        }

        problemDetail.setProperty("errors", errors)
        problemDetail.setProperty("timestamp", Instant.now())

        return problemDetail
    }
}