package com.challenge.payment

import com.challenge.FieldValidationException
import com.challenge.country.CountryRepository
import com.challenge.state.StateRepository
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/purchase")
class PurchaseUseCase(
    private val countryRepository: CountryRepository,
    private val stateRepository: StateRepository
) {

    @PostMapping
    fun execute(@RequestBody @Valid request: PaymentRequestDto) {
        val country = countryRepository.findById(request.location.countryId)
        if (!country.isPresent) throw FieldValidationException("location.countryId", "Country not found")
        if (request.location.stateId != null && !stateRepository.existsByIdAndCountryId(
                request.location.stateId,
                request.location.countryId
            )
        ) throw FieldValidationException("location.stateId", "State is not related to this Country")
        println(request)
    }
}