package com.challenge.shared.validators.document

import jakarta.validation.ConstraintValidator
import jakarta.validation.Validator
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import org.springframework.stereotype.Component

@Component
class BrazilianDocumentValidator(
    private val validator: Validator
) : ConstraintValidator<BrazilianDocumentValid, String> {

    override fun isValid(value: String?, context: jakarta.validation.ConstraintValidatorContext): Boolean {
        if (value.isNullOrBlank()) return true

        val digits = value.filter(Char::isDigit)

        val cpfValid = validator.validate(CpfHolder(digits)).isEmpty()
        if (cpfValid) return true

        val cnpjValid = validator.validate(CnpjHolder(digits)).isEmpty()
        if (cnpjValid) return true

        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate("CPF/CNPJ is invalid")
            .addConstraintViolation()
        return false
    }

    private data class CpfHolder(@field:CPF val value: String)
    private data class CnpjHolder(@field:CNPJ val value: String)
}