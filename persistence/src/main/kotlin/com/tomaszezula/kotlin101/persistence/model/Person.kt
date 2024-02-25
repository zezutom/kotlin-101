package com.tomaszezula.kotlin101.persistence.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "people")
class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String = "",

    var age: Int = 0
)


interface PersonRepository : JpaRepository<Person, Long>