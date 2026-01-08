package com.challenge.state

import com.challenge.IntegrationTest
import com.challenge.country.Country
import com.challenge.country.CountryRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

class RegisterStateUseCaseTest : IntegrationTest() {

    @Autowired
    lateinit var countryRepository: CountryRepository

    @Test
    @Transactional
    @Rollback
    fun `should create a new state and return 200`() {
        val country = countryRepository.save(Country("Brazil"))

        val payload = """
            {
              "name": "São Paulo",
              "countryId": ${country.id}
            }
        """.trimIndent()

        mockMvc.post("/states") {
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
    fun `should return 400 when state name is duplicated`() {
        val country = countryRepository.save(Country("Brazil"))

        val payload = """
            {
              "name": "São Paulo",
              "countryId": ${country.id}
            }
        """.trimIndent()

        mockMvc.post("/states") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/states") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.fields.name") {
                    value("State with name 'São Paulo' already exists")
                }
            }
    }

    @Test
    @Transactional
    @Rollback
    fun `should return 400 when state name is duplicated (case insensitive)`() {
        val country = countryRepository.save(Country("Brazil"))

        mockMvc.post("/states") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "São Paulo", "countryId": ${country.id}}"""
        }.andExpect {
            status { isOk() }
        }

        mockMvc.post("/states") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"name": "são paulo", "countryId": ${country.id}}"""
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.fields.name") {
                    value("State with name 'são paulo' already exists")
                }
            }
    }

    @Test
    @Transactional
    @Rollback
    fun `should return 400 when country does not exist`() {
        val payload = """
            {
              "name": "São Paulo",
              "countryId": 9999
            }
        """.trimIndent()

        mockMvc.post("/states") {
            contentType = MediaType.APPLICATION_JSON
            content = payload
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.fields.countryId") {
                    value("Country not found")
                }
            }
    }
}
