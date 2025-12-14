package com.challenge

open class DomainException(message: String) : RuntimeException(message)
open class FieldValidationException(val field: String, message: String) : RuntimeException(message)