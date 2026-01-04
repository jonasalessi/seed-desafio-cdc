package com.challenge.book

import com.challenge.NotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class GetBookByIdUseCase(
    private val bookRepository: BookRepository
) {
    @GetMapping("/{id}")
    fun execute(@PathVariable id: Long): BookByIdDto =
        bookRepository.findById(id, BookByIdDto::class.java) ?: throw NotFoundException("Book not found")
}