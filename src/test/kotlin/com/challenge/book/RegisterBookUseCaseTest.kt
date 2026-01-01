package com.challenge.book

import com.challenge.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.post

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS, scripts = ["/books/register-book.sql"])
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS, scripts = ["/books/register-book-clean.sql"])
class RegisterBookUseCaseTest : IntegrationTest() {

    @Test
    fun `should returns 400 when category does not exists`() {
        val payload = validPayload(categoryId = 0, authorId = 1, title = "New Book")

        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.categoryId") {
                    value("Category not found")
                }
            }
    }

    @Test
    fun `should returns 400 when author does not exists`() {
        val payload = validPayload(categoryId = 1, authorId = 0, title = "New Book")

        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.authorId") {
                    value("Author not found")
                }
            }
    }

    @Test
    fun `should not accept book with duplicated title`() {
        val payload = validPayload(categoryId = 1, authorId = 1, title = "New Book")

        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isOk() }
            }
        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.length()") {
                    value(1)
                }
                jsonPath("\$.fields.title") {
                    value("Book with title 'New Book' already exists")
                }
            }
    }

    @Test
    fun `should return 200 when book is created`() {
        val payload = validPayload(categoryId = 1, authorId = 1)

        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `should return 400 when required fields are missing`() {
        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = "{}"
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.length()") {
                    value(1)
                }
                jsonPath("\$.fields.title") {
                    value("Field is required")
                }
            }
    }

    @Test
    fun `should return 400 when required fields are blank`() {
        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = validPayload(title = "", summary = "", isbn = "", tableOfContents = "")
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.length()") {
                    value(4)
                }
                jsonPath("\$.fields.title") {
                    value("Title is required")
                }
                jsonPath("\$.fields.summary") {
                    value("Summary is required")
                }
                jsonPath("\$.fields.isbn") {
                    value("ISBN is required")
                }
                jsonPath("\$.fields.tableOfContents") {
                    value("Table of contents is required")
                }
            }
    }

    @Test
    fun `should return 400 when summary exceeds 500 characters`() {
        val longSummary = "x".repeat(501)
        val payload = validPayload(summary = longSummary)

        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.length()") {
                    value(1)
                }
                jsonPath("\$.fields.summary") {
                    value("Must have a maximum of 500 characters")
                }
            }
    }

    @Test
    fun `should return 400 when release date is not in the future`() {
        val payload = validPayload(releaseDate = "2000-01-01")

        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.length()") {
                    value(1)
                }
                jsonPath("\$.fields.releaseDate") {
                    value("Release date must be in the future")
                }
            }
    }

    @Test
    fun `should return 400 when price or pages are below minimum`() {
        val payload = validPayload(price = "19.99", numberOfPages = 99)

        mockMvc.post("/books") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.fields.length()") {
                    value(2)
                }
                jsonPath("\$.fields.price") {
                    value("Price must be at least 20")
                }
                jsonPath("\$.fields.numberOfPages") {
                    value("Number of pages must be at least 100")
                }
            }
    }

    private fun validPayload(
        title: String = "Clean Code",
        summary: String = "A handbook of Agile software craftsmanship.",
        tableOfContents: String = "# TOC - Chapter 1- Chapter 2",
        price: String = "39.90",
        numberOfPages: Int = 300,
        isbn: String = "978-0132350884",
        releaseDate: String = "2099-12-31",
        categoryId: Long = 1,
        authorId: Long = 1
    ): String = """
        {
          "title": "$title",
          "summary": "$summary",
          "tableOfContents": "$tableOfContents",
          "price": $price,
          "numberOfPages": $numberOfPages,
          "isbn": "$isbn",
          "releaseDate": "$releaseDate",
          "categoryId": $categoryId,
          "authorId": $authorId
        }
    """.trimIndent()

}