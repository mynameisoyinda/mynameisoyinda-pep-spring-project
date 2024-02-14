package com.example.repository;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event.ID;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository <Account, Integer>{
    
    Account findByUsername(String username);

    Account findByUsernameAndPassword (String username, String password);

    Optional <Account> findById(int account_id);

    

    Account getById (Integer account_id);
   
   // Account findAllById (Iterable<ID> account_id);

}
