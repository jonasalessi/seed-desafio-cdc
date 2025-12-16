package com.challenge.category

import jakarta.validation.constraints.NotBlank

class RegisterCategoryRequest(
    @field:NotBlank(message = "Name is required")
    val name: String
) {
    fun toEntity() = Category(name = name.trim())
}