package com.tomaszezula.kotlin101.persistence.service

import com.tomaszezula.kotlin101.persistence.model.Person
import com.tomaszezula.kotlin101.persistence.model.PersonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonService(
    private val personRepository: PersonRepository
) {
    @Transactional(readOnly = true)
    fun getPerson(personId: Long): Person {
        return personRepository.findById(personId)
            .orElseThrow { RuntimeException("Person not found!") }
    }

    @Transactional
    fun createPerson(name: String, age: Int): Person {
        val person = Person(name = name, age = age)
        return personRepository.saveAndFlush(person)
    }

    @Transactional
    fun updatePerson(personId: Long, name: String) {
        val person = personRepository.findById(personId)
            .orElseThrow { RuntimeException("Person not found!") }
        person.name = name

        // The person instance is automatically managed by Hibernate,
        // thanks to the @Transactional annotation,
        // meaning there's no need to explicitly save it.
    }

    @Transactional
    fun attachPerson(detachedPerson: Person): Person {
        return personRepository.saveAndFlush(detachedPerson)
    }

    @Transactional(readOnly = true)
    fun findPersonByName(name: String): List<Person> {
        return personRepository.findByName(name)
    }

    @Transactional
    fun updatePersonName(oldName: String, newName: String): Int {
        return personRepository.updateName(oldName, newName)
    }

    @Transactional(rollbackFor = [RuntimeException::class], noRollbackFor = [CertainNotSoSeriousException::class])
    fun deletePersonByName(name: String, postDelete: () -> Unit = {}): Int {
        val deletedCount = personRepository.deleteByName(name)
        postDelete()
        return deletedCount
    }
}

class CertainNotSoSeriousException(message: String) : Exception(message)
