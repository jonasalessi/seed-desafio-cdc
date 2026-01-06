package com.challenge.book

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun existsByTitleIgnoreCase(title: String): Boolean
    fun <T> findById(id: Long, projection: Class<T>): T?
    fun <T> findBooksBy(projection: Class<T>): List<T>
}