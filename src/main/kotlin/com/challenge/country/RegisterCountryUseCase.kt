package com.challenge.country

import com.challenge.FieldValidationException
import jakarta.validation.Valid
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/countries")
class RegisterCountryUseCase(private val repository: CountryRepository) {

    @PostMapping
    @Transactional
    fun execute(@RequestBody @Valid request: RegisterCountryRequestDto) {
        if (repository.existsByNameIgnoreCase(request.name)) {
            throw FieldValidationException("name", "Country with name '${request.name}' already exists")
        }
        repository.save(request.toEntity())
    }
}
