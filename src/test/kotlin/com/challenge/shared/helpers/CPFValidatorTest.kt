package com.challenge.shared.helpers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CPFValidatorTest {

    @Test
    fun `should return true for valid CPF`() {
        val validCPF = listOf("277.855.990-68", "844.004.660-00", "697.419.002-60", "519.017.975-38")

        validCPF.forEach {
            assertThat(CPFValidator.isValid(it))
                .withFailMessage { "CPF $it should be valid" }.isTrue()
        }
    }

    @Test
    fun `should return false for invalid CPF`() {
        val validCPF = listOf("15.181.220/0001-65", "519.017.975-31", "690.419.002-60")

        validCPF.forEach {
            assertThat(CPFValidator.isValid(it))
                .withFailMessage { "CPF $it should be invalid" }.isFalse()
        }
    }
}