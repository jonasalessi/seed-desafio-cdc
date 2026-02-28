package com.challenge.state

import com.challenge.country.Country
import jakarta.validation.constraints.NotBlank

class RegisterStateRequestDto(
    @field:NotBlank(message = "Name is required")
    val name: String,
    val countryId: Int
) {
    fun toEntity(country: Country) = State(name = name.trim(), country = country)
}
