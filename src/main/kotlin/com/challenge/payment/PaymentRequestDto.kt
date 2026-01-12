package com.challenge.payment

import com.challenge.shared.validators.ValidCPForCNPJ
import jakarta.validation.constraints.Email

data class PaymentRequestDto(
    @Email(message = "Email must be a well-formed email address")
    val email: String,
    @ValidCPForCNPJ
    val document: String
)