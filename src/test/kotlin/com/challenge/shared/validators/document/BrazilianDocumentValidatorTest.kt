package com.challenge.shared.validators.document

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*

class BrazilianDocumentValidatorTest {

    private lateinit var validator: BrazilianDocumentValidator
    private lateinit var context: ConstraintValidatorContext
    private lateinit var beanValidator: Validator
    private lateinit var violationBuilder: ConstraintValidatorContext.ConstraintViolationBuilder

    @BeforeEach
    fun setUp() {
        beanValidator = mock(Validator::class.java)
        validator = BrazilianDocumentValidator(beanValidator)
        context = mock(ConstraintValidatorContext::class.java)
        violationBuilder = mock(ConstraintValidatorContext.ConstraintViolationBuilder::class.java)

        `when`(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder)
        `when`(violationBuilder.addConstraintViolation()).thenReturn(context)
    }

    @Test
    fun `should return true when value is null or blank`() {
        assertTrue(validator.isValid(null, context))
        assertTrue(validator.isValid("", context))
        assertTrue(validator.isValid("   ", context))
    }

    @Test
    fun `should return true for valid CPF`() {
        val validCpf = "19100000000"
        `when`(beanValidator.validate(any<Any>())).thenReturn(emptySet())
        assertTrue(validator.isValid(validCpf, context))
    }

    @Test
    fun `should return false for invalid CPF`() {
        val invalidCpf = "12345678901"
        `when`(beanValidator.validate(any<Any>())).thenReturn(setOf(mock()))
        assertFalse(validator.isValid(invalidCpf, context))
        verify(context).disableDefaultConstraintViolation()
        verify(context).buildConstraintViolationWithTemplate("CPF/CNPJ is invalid")
    }

    @Test
    fun `should return true for valid CNPJ`() {
        val validCnpj = "11444777000161"
        `when`(beanValidator.validate(any<Any>())).thenReturn(emptySet())
        assertTrue(validator.isValid(validCnpj, context))
    }

    @Test
    fun `should return false for invalid CNPJ`() {
        val invalidCnpj = "11111111111111"
        `when`(beanValidator.validate(any<Any>())).thenReturn(setOf(mock()))
        assertFalse(validator.isValid(invalidCnpj, context))
        verify(context).disableDefaultConstraintViolation()
        verify(context).buildConstraintViolationWithTemplate("CPF/CNPJ is invalid")
    }

    @Test
    fun `should return false for random string of length 11`() {
        `when`(beanValidator.validate(any<Any>())).thenReturn(setOf(mock()))
        assertFalse(validator.isValid("11111111112", context))
    }
}
