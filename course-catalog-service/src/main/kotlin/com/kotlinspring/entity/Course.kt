package com.kotlinspring.entity

import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class Course(
    @Id  val id: Int?,
    val name: String,
    val author: String
)