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
class RegisterCategoryUseCase(private val repository: CategoryRepository) {

    @PostMapping
    @Transactional
    fun execute(@RequestBody @Valid request: RegisterCategoryRequestDto) {
        if (repository.existsByNameIgnoreCase(request.name)) {
            throw FieldValidationException("name", "Name '${request.name}' is duplicated")
        }
        repository.save(request.toEntity())
    }
}
