package com.challenge.book

import com.challenge.author.Author
import com.challenge.category.Category
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "books", uniqueConstraints = [UniqueConstraint(columnNames = ["title"], name = "uk_book_title")])
class Book(
    @NotBlank
    @Column(nullable = false)
    val title: String,
    @NotBlank
    @Column(length = 500, nullable = false)
    val summary: String,
    @Lob
    @Column(name = "table_contents")
    val tableOfContents: String,
    @Column(nullable = false)
    val price: BigDecimal,
    @Column(nullable = false, name = "number_pages")
    val numberOfPages: Int,
    @NotBlank
    @Column(nullable = false)
    val isbn: String,
    @Column(nullable = false)
    val releaseDate: LocalDate,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    val author: Author,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}