package com.example.restdemo20;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;
    private final PersonService service;

    public PersonController(PersonRepository personRepository, PersonService service) {
        this.personRepository = personRepository;
        this.service = service;
    }


    @GetMapping("/{personId}")
    public Optional<Person> findPersonById(@PathVariable int personId) {
        return service.findPersonById(personId);
    }

    @DeleteMapping("/{personId}/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable int personId, @PathVariable int messageId) {
        return service.deleteMessage(personId, messageId);
    }

    @PostMapping("/{personId}/messages")
    public ResponseEntity<?> addMessage(@PathVariable int personId, @RequestBody Message message) {
        return service.addMessage(personId, message);
    }

    @GetMapping("/{personId}/messages")
    public ResponseEntity<?> getMessageList(@PathVariable int personId) {
        return service.getMessageList(personId);
    }

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") int personId, @RequestBody Person updatedPerson) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            Person existingPerson = optionalPerson.get();
            existingPerson.setFirstname(updatedPerson.getFirstname());
            existingPerson.setSurname(updatedPerson.getSurname());
            existingPerson.setLastname(updatedPerson.getLastname());
            existingPerson.setBirthday(updatedPerson.getBirthday());
            existingPerson.setMessages(updatedPerson.getMessages());
            Person updatedExistingPerson = personRepository.save(existingPerson);
            return ResponseEntity.ok(updatedExistingPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable(value = "id") int personId) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            personRepository.delete(optionalPerson.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}