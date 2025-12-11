package com.challenge.author

import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long> {
    fun existsByEmail(email: String): Boolean
}
