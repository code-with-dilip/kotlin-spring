package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.repository.CourseRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
internal class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp(){
        courseRepository.deleteAll()

        val courses = listOf(
            Course(null,
                "Build RestFul APis using Spring Boot and Kotlin", "Development" ),
            Course(null,
                "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development" )
        )
        courses.forEach {
            courseRepository.save(it)
        }

    }

    @Test
    fun addCourse() {
        //given
        val courseDTO = CourseDTO(null,
            "Build RestFul APis using Spring Boot and Kotlin", "Dilip Sundarraj" )

        //when
        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        //then
        assertTrue {
            savedCourseDTO!!.id!=null
        }
    }

    @Test
    internal fun retrieveAllCourses() {


        val courseDTOs = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(2, courseDTOs!!.size)

    }
}