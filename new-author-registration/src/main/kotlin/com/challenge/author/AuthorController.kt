package com.challenge.author

import com.challenge.DomainException
import jakarta.validation.Valid
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
class AuthorController(private val repository: AuthorRepository) {

    @PostMapping
    fun create(@RequestBody @Valid request: NewAuthorRequest) {
        try {
            repository.save(request.toEntity())
        } catch (_: DataIntegrityViolationException) {
            throw DomainException("Email is duplicated")
        }
    }
}
