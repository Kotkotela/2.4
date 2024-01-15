package com.example.restdemo20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Iterable<Person> getPersons() {
        return repository.findAll();
    }

    public Optional<Person> findPersonById(int personId) {
        return repository.findById(personId);
    }

    public Person addPerson(Person person) {
        return repository.save(person);
    }

    public ResponseEntity<Person> updatePerson(int personId, Person person) {
        HttpStatus status = repository.existsById(personId) ? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<>(repository.save(person), status);
    }
    // Реализовать удаление сообщения у пользователя по его id
    public ResponseEntity<?> deleteMessage(int personId, int messageId) {
        Optional<Person> optionalPerson = repository.findById(personId);
        if (!optionalPerson.isPresent()) {
            return ResponseEntity.badRequest().body("Пользователь с указанным id не существует");
        }

        Person person = optionalPerson.get();
        Optional<Message> optionalMessage = person.getMessages().stream()
                .filter(message -> message.getmessageId() == messageId)
                .findFirst();

        if (!optionalMessage.isPresent()) {
            return ResponseEntity.badRequest().body("Сообщение с указанным id не найдено");
        }

        Message message = optionalMessage.get();
        person.getMessages().remove(message);
        repository.save(person);

        return ResponseEntity.ok(person);
    }
    //При добавлении сообщения реализовать проверку на наличие пользователя в БД, если пользователь с нужным id отсутствует в базе вернуть статус BAD_REQUEST
    public ResponseEntity<?> addMessage(int personId, Message message) {
        Optional<Person> optionalPerson = repository.findById(personId);
        if (optionalPerson.isPresent()) {
            return ResponseEntity.badRequest().body("Пользователь с указанным id не существует");
        }

        Person person = optionalPerson.get();
        message.setPerson(person);
        message.setTime(LocalDateTime.now());
        person.addMessage(message);
        repository.save(person);

        return ResponseEntity.ok(person);
    }
    // Реализовать вывод списка сообщений у конкретного пользователя.
    public ResponseEntity<?> getMessageList(int personId) {
        Optional<Person> optionalPerson = repository.findById(personId);
        if (optionalPerson.isPresent()) {
            return ResponseEntity.badRequest().body("Пользователь с указанным id не существует");
        }

        Person person = optionalPerson.get();
        List<Message> messageList = person.getMessages();

        return ResponseEntity.ok(messageList);
    }
}