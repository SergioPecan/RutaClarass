package com.application.services;

import com.application.entities.User;
import com.application.entities.Message;
import com.application.repositories.UserGenericRepository;
import com.application.repositories.MessageGenericRepository;
import com.application.services.exceptions.ObjectNotFoundException;
import com.application.services.exceptions.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageGenericRepository messageRepository;

    @Autowired
    private UserGenericRepository botRepository;

    public Message findById(String id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.orElseThrow(() -> new ObjectNotFoundException("Message not found: "+id));
    }

    public List<Message> findByConversationId(String conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }

    public Message insert(Message message) {
        insertUpdateValidations(message);
        return messageRepository.insert(message);
    }

    private void insertUpdateValidations(Message message) {
        validateToOrFromBot(message);
    }

    private void validateToOrFromBot(Message message) {
        Optional<User> from = botRepository.findById(message.getFrom());
        Optional<User> to = botRepository.findById(message.getTo());

        if(from.isEmpty() && to.isEmpty()) {
            throw new ValidateException("To or From must be a bot ID");
        }
    }


}
