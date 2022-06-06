package com.example.gcphelloworld.controllers;

import com.example.gcphelloworld.models.Message;
import com.example.gcphelloworld.repositories.MessageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RestController
class MessageController {
    private final MessageRepository messageRepository;

    MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/api/v1/messages")
    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll(Sort.by(Sort.Direction.ASC, "creationTimestamp"));
    }

    @GetMapping("/api/v1/messages/{id}")
    public Message getMessage(@PathVariable String id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found with id " + id));
    }

    @GetMapping("/api/v1/messages/latest")
    public Message getLatestMessage() {
        Message latest = messageRepository.findFirstByOrderByCreationTimestampDesc();
        if (latest == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No messages found");
        } else {
            return latest;
        }
    }

    @PostMapping("/api/v1/messages")
    public String createMessage(@RequestBody Message message) {
        // Spanner currently does not auto generate IDs; generate UUID on new messages
        message.setMessageId(UUID.randomUUID().toString());
        message.setCreationTimestamp(LocalDateTime.now(ZoneOffset.UTC));

        Message saved = messageRepository.save(message);
        return saved.getMessageId();
    }
}
