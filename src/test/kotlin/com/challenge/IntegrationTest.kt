package com.challenge

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import tools.jackson.databind.ObjectMapper

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var mapper: ObjectMapper

    fun <T> MockMvcResultMatchersDsl.contentWith(expected: T) {
        this.content { json(mapper.writeValueAsString(expected)) }
    }
}