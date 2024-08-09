package com.tomaszezula.kotlin101.persistence

import com.tomaszezula.kotlin101.persistence.model.Person
import com.tomaszezula.kotlin101.persistence.web.CreateOrUpdatePersonRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonIntegrationTest : PersistenceTest() {

    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private lateinit var person: Person

    private val url: String
        get() = "http://localhost:$port"

    @BeforeEach
    fun beforeEach() {
        // Create a new person before each test
        val request = CreateOrUpdatePersonRequest(
            name = "John",
            age = 30
        )
        person = restTemplate.postForObject("$url/people", request, Person::class.java)
        verifyPerson(person, request.name, request.age)
    }

    @Test
    fun `should update person name`() {
        val newName = "Jane"
        restTemplate.put("$url/people/${person.id}/$newName", null)
        val updatedPerson = restTemplate.getForObject("$url/people/${person.id}", Person::class.java)
        verifyPerson(updatedPerson, newName, person.age)
    }

    @Test
    fun `should update all person attributes`() {
        val request = CreateOrUpdatePersonRequest(
            name = "Jane",
            age = 25
        )
        val updatedPerson = restTemplate.postForObject("$url/people/${person.id}", request, Person::class.java)
        verifyPerson(updatedPerson, request.name, request.age)
    }

    private fun verifyPerson(person: Person, expectedName: String, expectedAge: Int) {
        assertNotNull(person.id, "Person id should not be null")
        assertEquals(expectedName, person.name, "Person name should match")
        assertEquals(expectedAge, person.age, "Person age should match")
    }
}
