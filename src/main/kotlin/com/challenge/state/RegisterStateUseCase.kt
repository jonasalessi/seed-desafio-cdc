package com.challenge.state

import com.challenge.FieldValidationException
import com.challenge.country.CountryRepository
import jakarta.validation.Valid
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/states")
class RegisterStateUseCase(
    private val repository: StateRepository,
    private val countryRepository: CountryRepository
) {

    @PostMapping
    @Transactional
    fun execute(@RequestBody @Valid request: RegisterStateRequestDto) {
        if (repository.existsByNameIgnoreCase(request.name)) {
            throw FieldValidationException("name", "State/Province/Region with name '${request.name}' already exists")
        }

        val country = countryRepository.findByIdOrNull(request.countryId)
            ?: throw FieldValidationException("countryId", "Country not found")

        repository.save(request.toEntity(country))
    }
}
