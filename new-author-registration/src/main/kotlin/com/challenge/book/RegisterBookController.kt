package com.challenge.book

import com.challenge.FieldValidationException
import com.challenge.author.AuthorRepository
import com.challenge.category.CategoryRepository
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class RegisterBookController(
    private val categoryRepository: CategoryRepository,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) {

    @PostMapping
    fun create(@RequestBody @Valid request: RegisterBookRequest) {
        val category = categoryRepository.findById(request.categoryId)
        if (!category.isPresent) {
            throw FieldValidationException("categoryId", "Category not found")
        }
        val author = authorRepository.findById(request.authorId)
        if (!author.isPresent) {
            throw FieldValidationException("authorId", "Author not found")
        }
        if (bookRepository.existsByTitleIgnoreCase(request.title)) {
            throw FieldValidationException("title", "Book with title '${request.title}' already exists")
        }
        bookRepository.save(request.toEntity(category.get(), author.get()))
    }
}