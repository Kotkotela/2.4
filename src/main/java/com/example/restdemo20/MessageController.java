package com.example.restdemo20;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Message> getMessageById(@PathVariable int id) {
        return messageRepository.findById(id);
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

    @PutMapping("/{id}")
    public Message updateMessage(@PathVariable int id, @RequestBody Message updatedMessage) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setTitle(updatedMessage.getTitle());
            message.setText(updatedMessage.getText());
            message.setTime(updatedMessage.getTime());
            return messageRepository.save(message);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteMessage(@PathVariable int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
