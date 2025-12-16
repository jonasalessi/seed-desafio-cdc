package com.challenge.author

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class RegisterAuthorRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email is invalid")
    val email: String,

    @field:NotBlank(message = "Description is required")
    @field:Size(max = 400, message = "Description must be at most 400 characters long")
    val description: String
) {
    fun toEntity() = Author(
        name = name.trim(),
        email = email.trim(),
        description = description.trim()
    )
}
