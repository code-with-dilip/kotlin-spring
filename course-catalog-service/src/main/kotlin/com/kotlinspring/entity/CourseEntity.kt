package com.kotlinspring.entity

import javax.persistence.*


@Entity(name="COURSES")
data class CourseEntity(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Int?,
   /* val name: String,
    val category: String*/
    var name: String,
    var category: String
)