package com.tomaszezula.kotlin101.persistence

import com.tomaszezula.kotlin101.persistence.model.Person
import com.tomaszezula.kotlin101.persistence.model.PersonRepository
import com.tomaszezula.kotlin101.persistence.service.CertainNotSoSeriousException
import com.tomaszezula.kotlin101.persistence.service.PersonService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class PersonServiceTest : PersistenceTest() {

    @Autowired
    private lateinit var personService: PersonService

    @Autowired
    private lateinit var personRepository: PersonRepository

    private lateinit var savedPerson: Person

    @BeforeEach
    fun setUp() {
        // Given
        val person = Person(name = "John", age = 30)
        savedPerson = personRepository.save(person)
    }

    @AfterEach
    fun tearDown() {
        personRepository.deleteAll()
    }

    @Test
    fun `should find person by name`() {
        // When
        val foundPerson = personService.findPersonByName("John")

        // Then
        assertEquals(1, foundPerson.size)
        assertEquals(savedPerson, foundPerson[0])
    }

    @Test
    fun `should update person name`() {
        // When
        val updatedRows = personService.updatePersonName("John", "Johnny")

        // Then
        assertEquals(1, updatedRows)
        val foundPerson = personService.findPersonByName("Johnny")
        assertEquals(1, foundPerson.size)
        assertEquals(savedPerson.apply { name = "Johnny" }, foundPerson[0])
    }

    @Test
    fun `should delete person by name`() {
        // When
        val deletedRows = personService.deletePersonByName("John")

        // Then
        assertEquals(1, deletedRows)
        val foundPerson = personService.findPersonByName("John")
        assertEquals(0, foundPerson.size)
    }

    @Test
    fun `should not rollback for certain not so serious exception`() {
        // When
        val thrown = assertThrows<CertainNotSoSeriousException> {
            personService.deletePersonByName("John") {
                throw CertainNotSoSeriousException("This is not so serious")
            }
        }
        assertEquals("This is not so serious", thrown.message)

        // Then
        val foundPerson = personService.findPersonByName("John")
        assertEquals(0, foundPerson.size)
    }

    @Test
    fun `should rollback for runtime exception`() {
        // When
        assertThrows<RuntimeException> {
            personService.deletePersonByName("John") {
                throw RuntimeException("This is serious")
            }
        }

        // Then
        val foundPerson = personService.findPersonByName("John")
        assertEquals(1, foundPerson.size)
    }
}
