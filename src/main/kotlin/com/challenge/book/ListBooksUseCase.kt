package com.challenge.book

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class ListBooksUseCase(
    private val bookRepository: BookRepository
) {
    @GetMapping
    fun execute() = bookRepository.findAll().map { BookDto(it.id!!, it.title)}
}