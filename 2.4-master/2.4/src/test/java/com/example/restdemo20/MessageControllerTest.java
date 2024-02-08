package com.example.restdemo20;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageRepository messageRepository;

    @Test
    public void testGetAllMessages() {
        List<Message> expectedMessages = Arrays.asList(
                new Message(1, "Title 1", "Text 1", LocalDateTime.now()),
                new Message(2, "Title 2", "Text 2", LocalDateTime.now())
        );
        Mockito.when(messageRepository.findAll()).thenReturn(expectedMessages);

        List<Message> actualMessages = messageController.getAllMessages();

        Assertions.assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testGetMessageById() {
        int messageId = 1;
        Optional<Message> expectedMessage = Optional.of(new Message(messageId, "Title", "Text", LocalDateTime.now()));
        Mockito.when(messageRepository.findById(messageId)).thenReturn(expectedMessage);

        Optional<Message> actualMessage = messageController.getMessageById(messageId);

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testCreateMessage() {
        Message newMessage = new Message(1, "New Title", "New Text", LocalDateTime.now());
        Mockito.when(messageRepository.save(newMessage)).thenReturn(newMessage);

        Message createdMessage = messageController.createMessage(newMessage);

        Assertions.assertEquals(newMessage, createdMessage);
    }

    @Test
    public void testUpdateMessage() {
        int messageId = 1;
        Message existingMessage = new Message(messageId, "Old Title", "Old Text", LocalDateTime.now());
        Message updatedMessage = new Message(messageId, "New Title", "New Text", LocalDateTime.now());
        Optional<Message> expectedMessage = Optional.of(existingMessage);
        Mockito.when(messageRepository.findById(messageId)).thenReturn(expectedMessage);
        Mockito.when(messageRepository.save(existingMessage)).thenReturn(existingMessage);

        Message actualMessage = messageController.updateMessage(messageId, updatedMessage);

        Assertions.assertEquals(existingMessage, actualMessage);
        Assertions.assertEquals(updatedMessage.getTitle(), actualMessage.getTitle());
        Assertions.assertEquals(updatedMessage.getText(), actualMessage.getText());
        Assertions.assertEquals(updatedMessage.getTime(), actualMessage.getTime());
    }

    @Test
    public void testDeleteMessage() {
        int messageId = 1;
        Optional<Message> expectedMessage = Optional.of(new Message(messageId, "Title", "Text", LocalDateTime.now()));
        Mockito.when(messageRepository.findById(messageId)).thenReturn(expectedMessage);

        boolean isDeleted = messageController.deleteMessage(messageId);

        Assertions.assertTrue(isDeleted);
        Mockito.verify(messageRepository, Mockito.times(1)).deleteById(messageId);
    }
}
