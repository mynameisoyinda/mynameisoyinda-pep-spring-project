package com.example.repository;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository <Message, Integer> {

    List<Message> findAll();

    Optional<Message> findById(int messageId); 


    @Transactional
    @Modifying
    @Query("DELETE FROM Message m WHERE m.message_id = ?1")
    int deleteById (int message_id);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.message_text = ?1 WHERE m.message_id = ?2")
    int updateMessageTextById(int message_id, String message_text);

    @Query ("SELECT m FROM Message m WHERE m.posted_by = ?1")
    List<Message> findByPostedBy(int posted_by);





    
}
