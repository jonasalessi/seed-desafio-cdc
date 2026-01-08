package com.challenge.country

import com.challenge.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

class RegisterCountryUseCaseTest : IntegrationTest() {

    @Test
    @Transactional
    @Rollback
    fun `should create a new country and return 200`() {
        val payload = """
            {
              "name": "Brazil"
            }
        """.trimIndent()

        mockMvc.post("/countries") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @Transactional
    @Rollback
    fun `should return 400 when name is duplicated`() {
        val payload = """
            {
              "name": "Brazil"
            }
        """.trimIndent()

        mockMvc.post("/countries") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/countries") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.fields.name") {
                    value("Country with name 'Brazil' already exists")
                }
            }
    }

    @Test
    @Transactional
    @Rollback
    fun `should return 400 when name is duplicated (case insensitive)`() {
        mockMvc.post("/countries") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "Brazil"}"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/countries") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "brazil"}"""
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.fields.name") {
                    value("Country with name 'brazil' already exists")
                }
            }
    }
}
