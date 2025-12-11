package com.challenge

open class DomainException(message: String) : Exception(message)

class EmailDuplicatedException(email: String) : DomainException("Email '$email' is duplicated")