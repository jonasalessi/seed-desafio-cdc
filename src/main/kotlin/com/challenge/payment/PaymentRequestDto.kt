package com.challenge.payment

import com.challenge.shared.validators.document.BrazilianDocumentValid
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class PaymentRequestDto(
    @Email(message = "Email must be a well-formed email address")
    val email: String,
    @NotBlank(message = "Document is required")
    @BrazilianDocumentValid
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
    @field:NotBlank(message = "Address is required")
    val address: String,
    @field:NotBlank(message = "Address complement is required")
    val complement: String,
    @field:NotBlank(message = "City is required")
    val city: String,
    @field:Min(message = "Country is not valid", value = 1)
    val countryId: Int,
    val stateId: Long?,
    @field:NotBlank(message = "Postal code is required")
    val postalCode: String
)