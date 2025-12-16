package com.challenge.book

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<Book, Long> {
    fun existsByTitleIgnoreCase(title: String): Boolean
}