package com.example.restdemo20;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private int messageId;
    private String title;
    private String text;
    private LocalDateTime time;
    @ManyToOne
    @JsonIgnore
    private Person person;

    public Message(int messageId, String title, String text, LocalDateTime time) {
        this.messageId = messageId;
        this.title = title;
        this.text = text;
        this.time = time;
    }

    public Message(String title, String text, LocalDateTime time, Person person) {
        this.title = title;
        this.text = text;
        this.time = time;
        this.person = person;
    }


    public Message(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Message() {

    }


    public int getmessageId() {
        return messageId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


    public void setPerson(Person person) {
        this.person = person;
    }

}
