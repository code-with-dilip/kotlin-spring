package com.kotlinspring.controller

import com.kotlinspring.service.GreetingService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
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

        Mockito.`when`(greetingService.retrieveGreeting(Mockito.anyString())).thenCallRealMethod()

        val result =webTestClient.get()
            .uri("/v1/greeting/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        assertEquals("Hello $name!", result.responseBody)

    }
}