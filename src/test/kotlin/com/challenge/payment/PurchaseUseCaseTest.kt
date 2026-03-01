package com.challenge.payment

import com.challenge.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class PurchaseUseCaseTest : IntegrationTest() {

    @Test
    fun `should return 400 when invalid input are passed`() {
        mockMvc.post("/purchase") {
            contentType = MediaType.APPLICATION_JSON
            content = toJson(
                PaymentRequestDto(
                    email = "user@.com",
                    document = "12345678901",
                    firstName = "",
                    lastName = "",
                    phoneNumber = "",
                    location = LocationDto(
                        city = "",
                        address = "",
                        complement = "",
                        countryId = 0,
                        stateId = null,
                        postalCode = "",
                    )
                )
            )
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.length()") {
                    value(10)
                }
                jsonPath("\$.fields.email") {
                    value("Email must be a well-formed email address")
                }
                jsonPath("\$.fields.document") {
                    value("CPF/CNPJ is invalid")
                }
                jsonPath("\$.fields.phoneNumber") {
                    value("Phone number is required")
                }
                jsonPath("\$.fields.firstName") {
                    value("First name is required")
                }
                jsonPath("\$.fields.lastName") {
                    value("Last name is required")
                }
                jsonPath("\$.fields['location.address']") {
                    value("Address is required")
                }

                jsonPath("\$.fields['location.complement']") {
                    value("Address complement is required")
                }
                jsonPath("\$.fields['location.city']") {
                    value("City is required")
                }
                jsonPath("\$.fields['location.postalCode']") {
                    value("Postal code is required")
                }
                jsonPath("\$.fields['location.countryId']") {
                    value("Country is not valid")
                }
            }
    }
}