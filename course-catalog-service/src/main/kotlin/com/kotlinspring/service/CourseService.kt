package com.kotlinspring.service

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.CourseEntity
import com.kotlinspring.exception.CourseNotFoundException
import com.kotlinspring.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    fun addCourse(courseDTO: CourseDTO): CourseDTO {

        val courseEntity = courseDTO.let {
            CourseEntity(null, it.name, it.category)
        }

        courseRepository.save(courseEntity)

        return courseEntity.let {
            CourseDTO(it.id!!, it.name, it.category)
        }
    }

    fun retrieveAllCourses() =
        courseRepository.findAll()
            .map {
                CourseDTO(it.id, it.name, it.category)
            }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {

        val existingCourse = courseRepository.findById(courseId)

        return if (existingCourse.isPresent) {
            existingCourse.get()
                .let {
                    it.category = courseDTO.category
                    it.name = courseDTO.name
                    courseRepository.save(it)
                    CourseDTO(it.id, it.name, it.category)
                }
        } else {
            throw CourseNotFoundException("No Course Found for the passed in Id $courseId")
        }

    }

    fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)
        if (existingCourse.isPresent) {
            existingCourse.get()
                .let {
                    courseRepository.deleteById(courseId)
                }
        } else {
            throw CourseNotFoundException("No Course Found for the passed in Id $courseId")
        }


    }


}