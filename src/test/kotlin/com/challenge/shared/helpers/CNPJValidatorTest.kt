package com.challenge.shared.helpers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CNPJValidatorTest {

    @Test
    fun `should return true for valid CNPJ`() {
        val validCNPJ = listOf("15.181.200/0001-65", "43.085.101/0001-15")

        validCNPJ.forEach {
            assertThat(CNPJValidator.isValid(it))
                .withFailMessage { "CNPJ $it should be valid" }.isTrue()
        }
    }

    @Test
    fun `should return false for invalid CNPJ`() {
        val validCNPJ = listOf("15.181.220/0001-65", "43.082.101/0001-15", "43.082.101/0001")

        validCNPJ.forEach {
            assertThat(CNPJValidator.isValid(it))
                .withFailMessage { "CNPJ $it should be invalid" }.isFalse()
        }
    }
}