package com.challenge.category

import com.challenge.FieldValidationException
import jakarta.validation.Valid
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class RegisterCategoryController(private val repository: CategoryRepository) {

    @PostMapping
    @Transactional
    fun create(@RequestBody @Valid request: RegisterCategoryRequest) {
        if (repository.existsByNameIgnoreCase(request.name)) {
            throw FieldValidationException("name", "Name '${request.name}' is duplicated")
        }
        repository.save(request.toEntity())
    }
}
