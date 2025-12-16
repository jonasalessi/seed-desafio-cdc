package com.challenge.book

import com.challenge.author.Author
import com.challenge.category.Category
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDate

class RegisterBookRequestDto(
    @NotBlank(message = "Title is required")
    val title: String,
    @NotBlank(message = "Summary is required")
    @Size(max = 500, message = "Must have a maximum of 500 characters")
    val summary: String,
    @NotBlank(message = "Table of contents is required")
    val tableOfContents: String,
    @DecimalMin(value = "20", message = "Price must be at least 20")
    val price: BigDecimal,
    @Min(value = 100, message = "Number of pages must be at least 100")
    val numberOfPages: Int,
    @NotBlank(message = "ISBN is required")
    val isbn: String,
    @Future(message = "Release date must be in the future")
    val releaseDate: LocalDate,
    val categoryId: Long,
    val authorId: Long
) {
    fun toEntity(category: Category, author: Author) = Book(
        title = title,
        summary = summary,
        tableOfContents = tableOfContents,
        price = price,
        numberOfPages = numberOfPages,
        isbn = isbn,
        releaseDate = releaseDate,
        category = category,
        author = author
    )
}
