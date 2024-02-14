package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


@RestController
@RequestMapping
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController (AccountService accountService,
                                 MessageService messageService)
                                 {
        this.accountService = accountService;
        this.messageService = messageService;
     
    
    }

       

    
    // User Registration Handler//
    @PostMapping ("/register")
    public ResponseEntity<Account> userRegistration(@RequestBody Account account) {
        if (accountService.verifyUsernameExists(account.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } 
        Account newAccount = accountService.createNewAccount(account);
        if (newAccount == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
       
           
    }

    @PostMapping ("/login")
    public ResponseEntity <Account> userLogin (@RequestBody Account account) {
        Account loginDetails = accountService.accountLogin(account.getUsername(), account.getPassword());
        if (loginDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  
        } else {
            return new ResponseEntity<>(loginDetails, HttpStatus.OK);
        }
          
           
    }

    @PostMapping ("/messages")
    public ResponseEntity <Message> createNewMessage (@RequestBody Message message) {
        Message newMessage = messageService.createNewMessage(message);
        if (newMessage == null) {
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newMessage, HttpStatus.OK);
    }

    @GetMapping ("/messages")
    public List <Message> retreiveAllMessages (Message message) {
       return messageService.getAllMessages();
    }

    @GetMapping ("/messages/{message_id}")
    public ResponseEntity <Message> retrieveMessageById (@PathVariable int message_id) {
       Optional<Message> messageById = messageService.getMessagebyId(message_id);
       if (messageById.isPresent()) {
        return new ResponseEntity<>(messageById.get(), HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping ("/messages/{message_id}")
    public ResponseEntity <?> deleteMessageById (@PathVariable int message_id) {
        int deletedRows = messageService.deleteMessageById(message_id);
        if (deletedRows > 0) {
            return new ResponseEntity<>(deletedRows, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    
    @PatchMapping ("/messages/{message_id}")
    public ResponseEntity <?> updateMessageById (@PathVariable int message_id, @RequestBody String message_text) {
            int updatedRows = messageService.updateMessageById(message_text, message_id);
            if (updatedRows > 0) {
                return new ResponseEntity<>(updatedRows, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
       
      
    }


    

    @GetMapping ("/accounts/{account_id}/messages") 
    public ResponseEntity<List<Message>> getMessagesByUser (@PathVariable int account_id) {
        List<Message> allMessagesByUser = messageService.getAllMessagesByUser(account_id);
        if (allMessagesByUser != null) {
            return new ResponseEntity<>(allMessagesByUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
       
} 
