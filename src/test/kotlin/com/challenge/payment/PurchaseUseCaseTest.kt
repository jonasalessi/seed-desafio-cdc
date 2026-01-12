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
                )
            )
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.length()") {
                    value(2)
                }
                jsonPath("\$.fields.email") {
                    value("Email must be a well-formed email address")
                }
                jsonPath("\$.fields.document") {
                    value("CPF is invalid")
                }
            }
    }
}