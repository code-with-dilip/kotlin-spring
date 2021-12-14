package com.kotlinspring.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class CourseEntity(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Int?,
   /* val name: String,
    val category: String*/
    var name: String,
    var category: String
)