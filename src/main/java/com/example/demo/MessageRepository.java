package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {


    @Query(value="select m from MessageEntity m  where (m.sender like ?1 and m.receiver like ?2) or (m.sender like ?2 and m.receiver like ?1) order by m.time")
    List<MessageEntity> loadmessage(String sender,String receiver);

    @Query(value="select m from MessageEntity m where m.sender like ?1 order by m.time DESC")
    List<MessageEntity> recent(String sender);

    @Query(value="select m from MessageEntity m where m.receiver like ?1 order by m.time DESC")
    List<MessageEntity> recent2(String sender);

    @Query(value="SELECT receiver FROM (SELECT receiver, time FROM message_entity WHERE sender LIKE ?1 UNION SELECT sender, time FROM message_entity WHERE receiver LIKE ?1) AS combined ORDER BY time DESC", nativeQuery = true)
    List<String> recentCombined(String sender);




}
