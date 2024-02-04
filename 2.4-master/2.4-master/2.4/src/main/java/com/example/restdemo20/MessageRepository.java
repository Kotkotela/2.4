package com.example.restdemo20;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAll();

    Optional<Message> findById(int id);

    Message save(Message message);

    void deleteById(int id);
}
