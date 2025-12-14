package com.challenge.author

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should create a new author and return 200`() {
        val payload = """
            {
              "name": "Alice",
              "email": "alice@example.com",
              "description": "Author of many books"
            }
        """.trimIndent()

        mockMvc.post("/authors") {
            contentType = MediaType.APPLICATION_PROBLEM_JSON
            content = payload
        }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `should return 400 with validation errors when required fields are missing`() {
        val payload = """
            {
              "name": "",
              "email": "",
              "description": ""
            }
        """.trimIndent()

         mockMvc.post("/authors") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.errors.length()") {
                    value(3)
                }
                jsonPath("\$.errors.name"){
                    value("Name is required")
                }
                jsonPath("\$.errors.email"){
                    value("Email is required")
                }
                jsonPath("\$.errors.description"){
                    value("Description is required")
                }
            }
    }

    @Test
    fun `should not accept email with invalid format`() {
        val payload = """
            {
              "name": "Alice",
              "email": "invalid-email",
              "description": "Author of many books"
            }
        """.trimIndent()

        mockMvc.post("/authors") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.errors.length()") {
                    value(1)
                }
                jsonPath("\$.errors.email"){
                    value("Email is invalid")
                }
            }
    }

    @Test
    fun `should not accept description longer than 400`(){
        val payload = """
            {
              "name": "Alice",
              "email": "alice.2@example.com",
              "description": "${"a".repeat(401)}"
            }
        """.trimIndent()

        mockMvc.post("/authors") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.errors.length()") {
                    value(1)
                }
                jsonPath("\$.errors.description"){
                    value("Description must be at most 400 characters long")
                }
            }
    }

    @Test
    fun `should not accept duplicated emails`() {
        val payload = """
            {
              "name": "New Author",
              "email": "newauthor@example.com",
              "description": "Some description"
            }
        """.trimIndent()

        mockMvc.post("/authors") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }.andExpect { status { isOk() } }

        mockMvc.post("/authors") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.errors.length()") {
                    value(1)
                }
                jsonPath("\$.errors.email") {
                    value("Email 'newauthor@example.com' is duplicated")
                }
            }
    }
}
