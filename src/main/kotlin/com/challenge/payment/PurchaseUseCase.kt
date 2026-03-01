package com.challenge.payment

import com.challenge.FieldValidationException
import com.challenge.country.CountryRepository
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/purchase")
class PurchaseUseCase(
    private val countryRepository: CountryRepository
) {

    @PostMapping
    fun execute(@RequestBody @Valid request: PaymentRequestDto) {
        val country = countryRepository.findById(request.location.countryId)
        if (!country.isPresent) throw FieldValidationException("location.countryId", "Country not found")
        println(request)
    }
}