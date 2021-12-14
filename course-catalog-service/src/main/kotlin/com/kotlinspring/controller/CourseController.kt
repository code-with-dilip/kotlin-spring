package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService :CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDTO: CourseDTO): CourseDTO {

        return courseService.addCourse(courseDTO)

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllCourses(): Iterable<CourseDTO> = courseService.retrieveAllCourses()


    @PutMapping("/{course_id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCourse(@RequestBody courseDTO: CourseDTO,
    @PathVariable("course_id") courseId : Int): CourseDTO = courseService.updateCourse(courseId, courseDTO)

}