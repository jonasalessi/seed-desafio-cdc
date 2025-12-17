package com.challenge.book

import com.challenge.FieldValidationException
import com.challenge.author.AuthorRepository
import com.challenge.category.CategoryRepository
import jakarta.validation.Valid
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
// ICP 11
@RestController
@RequestMapping("/books")
class RegisterBookUseCase(
    private val categoryRepository: CategoryRepository,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) { //3

    @PostMapping
    @Transactional
    fun execute(@RequestBody @Valid request: RegisterBookRequestDto) { //4
        val category = categoryRepository.findById(request.categoryId)
        if (!category.isPresent) { //6
            throw FieldValidationException("categoryId", "Category not found")
        }
        val author = authorRepository.findById(request.authorId)
        if (!author.isPresent) { //8
            throw FieldValidationException("authorId", "Author not found")//9
        }
        if (bookRepository.existsByTitleIgnoreCase(request.title)) {//11
            throw FieldValidationException("title", "Book with title '${request.title}' already exists")
        }
        bookRepository.save(request.toEntity(category.get(), author.get()))
    }
}