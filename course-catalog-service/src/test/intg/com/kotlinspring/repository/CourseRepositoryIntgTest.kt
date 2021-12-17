package com.kotlinspring.repository

import com.kotlinspring.entity.CourseEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp(){
        courseRepository.deleteAll()

        val courses = listOf(
            CourseEntity(null,
                "Build RestFul APis using SpringBoot and Kotlin", "Development" ),
            CourseEntity(null,
                "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development" ),
            CourseEntity(null,
                "Wiremock for Java Developers", "Development" )
        )
        courses.forEach {
            courseRepository.save(it)
        }

    }

    @Test
    fun findByNameContaining() {

        val courses = courseRepository.findByNameContaining("SpringBoot")

        println("courses  : $courses")
        assertEquals(2, courses.size)

    }

    @Test
    fun findCoursesByName() {

        val courses = courseRepository.findCoursesByName("SpringBoot")

        println("courses  : $courses")
        assertEquals(2, courses.size)

    }


    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesByName_approach2(name: String, expectedSize:  Int) {

        val courses = courseRepository.findCoursesByName(name)

        println("courses  : $courses")
        assertEquals(expectedSize, courses.size)

    }



    companion object {
        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(Arguments.arguments("SpringBoot", 2),
                Arguments.arguments("Wiremock", 1))

        }
    }
}