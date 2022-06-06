package com.example.gcphelloworld.controllers;

import com.example.gcphelloworld.models.Message;
import com.example.gcphelloworld.repositories.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    private final MessageRepository messageRepository;

    FrontendController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        Message latestMessage = messageRepository.findFirstByOrderByCreationTimestampDesc();
        if (latestMessage == null) {
            model.addAttribute("errorMessage", "No messages found in database");
            return "error";
        }

        String messageId = latestMessage.getMessageId();
        String messageContent = latestMessage.getMessage();
        String messageCreationTimestamp = latestMessage.getCreationTimestamp().toString();
        model.addAttribute("messageId", messageId);
        model.addAttribute("messageContent", messageContent);
        model.addAttribute("messageCreationTimestamp", messageCreationTimestamp);

        return "index";
    }
}
