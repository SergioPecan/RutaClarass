package com.application.repositories;

import com.application.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageGenericRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationId(String conversationId);
}
