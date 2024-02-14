package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    public MessageService (MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }


    public List<Message> getAllMessages () {
        return messageRepository.findAll();
    }

    public Optional <Message> getMessagebyId (int messageId) {
        return messageRepository.findById(messageId);
    }

    public Message createNewMessage (Message message) {
        if (!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255
            && accountRepository.findById(message.getPosted_by()).isPresent()) {
               return messageRepository.save(message);
            }
            return null;
    }

    public int deleteMessageById (int messageId) {
       return messageRepository.deleteById(messageId);
    }

    public int updateMessageById (String message_text, int message_id) {

        Optional<Message> findMessagefirst = messageRepository.findById(message_id);

        if (message_text.isBlank() || message_text.length() > 255 || !findMessagefirst.isPresent()){
            return 0;
        } return messageRepository.updateMessageTextById(message_text, message_id);
        
    }

    public List<Message> getAllMessagesByUser (int postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }
}
