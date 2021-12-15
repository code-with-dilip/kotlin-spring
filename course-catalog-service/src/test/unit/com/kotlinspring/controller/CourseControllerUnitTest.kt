package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.service.CourseService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(CourseController::class)
@AutoConfigureWebTestClient(timeout = "6000000")
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService


    @Test
    fun addCourse() {
        //given
        val courseDTO = CourseDTO(
            null,
            "Build RestFul APis using Spring Boot and Kotlin", "Dilip Sundarraj"
        )

        every { courseServiceMock.addCourse(any()) } returns CourseDTO(
            1,
            "Build RestFul APis using Spring Boot and Kotlin", "Dilip Sundarraj"
        )

        //when
        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        //then
        Assertions.assertTrue {
            savedCourseDTO!!.id != null
        }
    }
}