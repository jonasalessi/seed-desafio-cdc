package com.challenge.country

import jakarta.validation.constraints.NotBlank

class RegisterCountryRequestDto(
    @field:NotBlank(message = "Name is required")
    val name: String
) {
    fun toEntity() = Country(name = name.trim())
}
