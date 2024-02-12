package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
   // private Account account = new Account();
   private AccountRepository accountRepository;

   @Autowired
   public AccountService (AccountRepository accountRepository) {
      this.accountRepository = accountRepository;
   }

   //Method for handling user registration
   public Account createNewAccount(Account account) {
      if (account.getUsername().isBlank() && account.getPassword().length() < 4) {
            return null;
        } 
       return accountRepository.save(account);
   }

   public Account verifyUsernameExists (String username) {
      return accountRepository.findByUsername(username);
   }

   public Account accountLogin (String username, String password) {
      return accountRepository.findByUsernameAndPassword(username, password);  
   }
}
