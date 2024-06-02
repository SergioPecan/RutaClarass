package com.application.configs;

import com.application.repositories.MessageGenericRepository;
import com.application.repositories.UserGenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestsSeed implements CommandLineRunner {
    @Autowired
    private UserGenericRepository userRepository;

    @Autowired
    private MessageGenericRepository messageRepository;

    @Override
    public void run(String... args) throws Exception {
        // clean test database
        userRepository.deleteAll();
        messageRepository.deleteAll();
    }
}
