package com.challenge.book

import com.challenge.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.get

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS, scripts = ["/books/list-book.sql"])
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS, scripts = ["/books/list-book-clean.sql"])
class ListBooksUseCaseTest : IntegrationTest() {

    @Test
    fun `should return all books`() {
        mockMvc.get("/books")
            .andExpect {
                status { isOk() }
                jsonPath("$.length()") { value(2) }
                jsonPath("$[0].id") { value(1) }
                jsonPath("$[0].title") { value("Book A") }
                jsonPath("$[1].id") { value(2) }
                jsonPath("$[1].title") { value("Book B") }
            }
    }
}