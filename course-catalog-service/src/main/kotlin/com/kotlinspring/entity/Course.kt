package com.kotlinspring.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class Course(
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long?,
    val name: String,
    val category: String
)