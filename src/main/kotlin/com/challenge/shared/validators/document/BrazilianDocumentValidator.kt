package com.challenge.shared.validators

import com.challenge.shared.helpers.CNPJValidator
import com.challenge.shared.helpers.CPFValidator
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

@Component
class BrazilianDocumentValidator : ConstraintValidator<BrazilianDocumentValid, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value.isNullOrBlank()) {
            return true
        }
        if (value.length == 11 && !CPFValidator.isValid(value)) {
            addError(context, "CPF is invalid")
            return false
        }
        if (!CNPJValidator.isValid(value)) {
            addError(context, "CNPJ is invalid")
            return false
        }
        return true
    }

    private fun addError(context: ConstraintValidatorContext, message: String) {
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation()
    }
}