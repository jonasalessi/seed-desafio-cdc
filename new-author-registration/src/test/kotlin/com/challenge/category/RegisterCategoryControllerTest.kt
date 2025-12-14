package com.challenge.category

import com.challenge.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class RegisterCategoryControllerTest : IntegrationTest() {

    @Test
    fun `should create a new author and return 200`() {
        val payload = """
            {
              "name": "Category A"
            }
        """.trimIndent()

        mockMvc.post("/categories") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `should return 400 with validation error when name is missing`() {
        val payload = """
            {
              "name": ""
            }
        """.trimIndent()

        mockMvc.post("/categories") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.errors.length()") {
                    value(1)
                }
                jsonPath("\$.errors.name") {
                    value("Name is required")
                }
            }
    }

    @Test
    fun `should return 400 with validation error when name is duplicated`() {
        val payload = """
            {
              "name": "Category Duplicated"
            }
        """.trimIndent()

        mockMvc.post("/categories") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/categories") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("\$.errors.length()") {
                    value(1)
                }
                jsonPath("\$.errors.name") {
                    value("Name 'Category Duplicated' is duplicated")
                }
            }
    }
}