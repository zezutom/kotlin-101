package com.tomaszezula.kotlin101.persistence.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

@Entity
@Table(name = "people")
class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String = "",

    var age: Int = 0


) {
    override fun equals(other: Any?): Boolean {
        return if (this.id != 0L) return this.id == (other as Person).id
        else this === other
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}


interface PersonRepository : JpaRepository<Person, Long> {
    // Custom SELECT query using JPQL
    @Query("SELECT p FROM Person p WHERE p.name = :name")
    fun findByName(name: String): List<Person>

    // Custom UPDATE query using JPQL
    @Modifying
    @Query("UPDATE Person p SET p.name = :newName WHERE p.name = :oldName")
    fun updateName(oldName: String, newName: String): Int // Returns the number of entities updated

    // Custom DELETE query using JPQL
    @Modifying
    @Query("DELETE FROM Person p WHERE p.name = :name")
    fun deleteByName(name: String): Int // Returns the number of entities deleted
}
