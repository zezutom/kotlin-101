package com.tomaszezula.kotlin101.persistence.web

import com.tomaszezula.kotlin101.persistence.model.Person
import com.tomaszezula.kotlin101.persistence.service.PersonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/people")
class PersonController(
    private val personService: PersonService
) {

    @GetMapping("/{personId}")
    fun getPerson(@PathVariable personId: Long): ResponseEntity<Person> {
        val person = personService.getPerson(personId)
        return ResponseEntity.ok(person)
    }

    @PostMapping
    fun createPerson(@RequestBody request: CreateOrUpdatePersonRequest): ResponseEntity<Person> {
        val person = personService.createPerson(request.name, request.age)
        return ResponseEntity.ok(person)
    }

    @PutMapping("/{personId}/{name}")
    fun updatePersonName(
        @PathVariable personId: Long,
        @PathVariable name: String
    ): ResponseEntity<Void> {
        personService.updatePerson(personId, name)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{personId}")
    fun updatePerson(
        @PathVariable personId: Long,
        @RequestBody request: CreateOrUpdatePersonRequest
    ): ResponseEntity<Person> {
        // The `person` object here is detached since it's not managed by Hibernate
        val detachedPerson = Person(
            id = personId,
            name = request.name,
            age = request.age
        )
        val managedPerson = personService.attachPerson(detachedPerson)
        return ResponseEntity.ok(managedPerson)
    }
}

data class CreateOrUpdatePersonRequest(val name: String, val age: Int)