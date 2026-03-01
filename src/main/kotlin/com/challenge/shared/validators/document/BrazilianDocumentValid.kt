package com.challenge.shared.validators.document

import jakarta.validation.Constraint
import jakarta.validation.Payload
import jakarta.validation.ReportAsSingleViolation
import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import kotlin.reflect.KClass

@ConstraintComposition(CompositionType.OR)
@ReportAsSingleViolation
@CPF
@CNPJ
@Constraint(validatedBy = [])
annotation class BrazilianDocumentValid(
    val message: String = "CPF/CNPJ is invalid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)