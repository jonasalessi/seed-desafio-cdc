package com.challenge.payment

import com.challenge.shared.validators.BrazilianDocumentValid
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.br.CPF

data class PaymentRequestDto(
    @Email(message = "Email must be a well-formed email address")
    val email: String,
    @NotBlank(message = "Document is required")
    @BrazilianDocumentValid
    @CPF
    val document: String,
    @NotBlank(message = "First name is required")
    val firstName: String,
    @NotBlank(message = "Last name is required")
    val lastName: String,
    @Valid
    val location: LocationDto,
    @NotBlank(message = "Phone number is required")
    val phoneNumber: String,
)

data class LocationDto(
    @NotBlank(message = "Address is required")
    val address: String,
    @NotBlank(message = "Address complement is required")
    val complement: String,
    @NotBlank(message = "City is required")
    val city: String,
    @Min(message = "Country is not valid", value = 1)
    val countryId: Int,
    val stateId: Long?,
    @NotBlank(message = "Postal code is required")
    val postalCode: String
)