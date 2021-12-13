package com.kotlinspring.service

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    fun addCourse(courseDTO: CourseDTO) : CourseDTO{

        val courseEntity = courseDTO.let {
            Course(null, it.name, it.author)
        }

        courseRepository.save(courseEntity)

        return courseEntity.let {
            CourseDTO(it.id!!, it.name, it.author)
        }
    }


}