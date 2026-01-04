package com.challenge.book

import java.math.BigDecimal
import java.time.LocalDate

class BookByIdDto(
    val title: String,
    val summary: String,
    val authorName: String,
    val authorDescription: String,
    val tableOfContents: String,
    val price: BigDecimal,
    val numberOfPages: Int,
    val isbn: String,
    val releaseDate: LocalDate
)