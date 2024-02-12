package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    public MessageService (MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public List<Message> getAllMessages () {
        return messageRepository.findAll();
    }
}
