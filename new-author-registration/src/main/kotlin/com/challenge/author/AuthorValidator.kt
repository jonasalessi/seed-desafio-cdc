package com.challenge.author

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

@Component
class AuthorValidator(
    private val repository: AuthorRepository
) : ConstraintValidator<EmailUnique, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value.isNullOrBlank()) {
            return true
        }
        val exists = repository.existsByEmail(value)

        if (exists) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("Email '$value' is duplicated")
                .addConstraintViolation()
        }
        return !exists
    }
}