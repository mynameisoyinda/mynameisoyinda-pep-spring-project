package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
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
    private AccountRepository accountRepository;

    @Autowired
    public SocialMediaController (AccountService accountService,
                                 MessageService messageService,
                                 AccountRepository accountRepository) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.accountRepository = accountRepository;
    
    }

       

    
    // User Registration Handler//
    @PostMapping ("/register")
    public ResponseEntity<Account> userRegistration(@RequestBody Account account) {
        Account newAccount = accountService.createNewAccount(account);

        if (newAccount != null) {
            return new ResponseEntity<>(newAccount, HttpStatus.OK);
        }
            if (accountService.verifyUsernameExists(account.getUsername()) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } 
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping ("/login")
    public ResponseEntity <Account> userLogin (@RequestBody Account account) {
        Account loginDetails = accountService.accountLogin(account.getUsername(), account.getPassword());
        if (loginDetails == null); {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  
        } 
          return new ResponseEntity<>(loginDetails);
           
    }

}
