package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.CourseEntity
import com.kotlinspring.repository.CourseRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

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
            CourseEntity(null,
                "Build RestFul APis using Spring Boot and Kotlin", "Development" ),
            CourseEntity(null,
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
     fun retrieveAllCourses() {


        val courseDTOs = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println("courseDTOs : $courseDTOs")

        assertEquals(2, courseDTOs!!.size)

    }

    @Test
    fun updateCourse() {

        val courseEntity = CourseEntity(null,
            "Apache Kafka for Developers using Spring Boot", "Development" )
        courseRepository.save(courseEntity)
        val updatedCourseEntity = CourseEntity(null,
            "Apache Kafka for Developers using Spring Boot1", "Development" )

        val updatedCourseDTO = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", courseEntity.id)
            .bodyValue(updatedCourseEntity)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Apache Kafka for Developers using Spring Boot1", updatedCourseDTO?.name)

    }
}