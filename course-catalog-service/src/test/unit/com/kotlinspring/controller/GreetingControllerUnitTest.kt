package com.kotlinspring.controller

import com.kotlinspring.service.GreetingService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.platform.commons.util.ReflectionUtils
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.TestPropertySources
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.reactive.server.WebTestClient


@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {

    @MockBean
    lateinit var greetingService: GreetingService

    @Autowired
    lateinit var webTestClient: WebTestClient


    @Test
    fun retrieveGreeting() {

        val name = "dilip"

        Mockito.`when`(greetingService.retrieveGreeting(Mockito.anyString())).thenReturn("$name, Hello from default profile")

        val result =webTestClient.get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        assertEquals("$name, Hello from default profile", result.responseBody)

    }
}