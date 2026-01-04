package com.challenge.book

import com.challenge.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.get
import java.math.BigDecimal
import java.time.LocalDate

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS, scripts = ["/books/list-book.sql"])
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS, scripts = ["/books/list-book-clean.sql"])
class GetBookByIdUseCaseTest : IntegrationTest() {

    @Test
    fun `should return the book by id`() {
        val expected = BookByIdDto(
            title = "Book A",
            summary = "Summary A",
            authorName = "Author A",
            authorDescription = "Author bio",
            tableOfContents = "Table of Contents A",
            price = BigDecimal(10.0),
            numberOfPages = 100,
            isbn = "ISBN A",
            releaseDate = LocalDate.of(2022, 1, 1),
        )
        mockMvc.get("/books/1")
            .andExpect {
                status { isOk() }
                contentWith(expected)
            }
    }

    @Test
    fun `should return 404 when book not found`() {
        mockMvc.get("/books/11")
            .andExpect {
                status { isNotFound() }
            }
    }
}