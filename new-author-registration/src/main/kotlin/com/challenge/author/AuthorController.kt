package com.challenge.author

import com.challenge.EmailDuplicatedException
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
class AuthorController(private val repository: AuthorRepository) {

    @PostMapping
    fun create(@RequestBody @Valid request: NewAuthorRequest) {
        if (repository.existsByEmail(request.email)) {
            throw EmailDuplicatedException(request.email)
        }
        repository.save(request.toEntity())
    }
}
