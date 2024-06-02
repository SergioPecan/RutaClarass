package com.application;

import com.application.dtos.MessageRequestDto;
import com.application.entities.Message;
import com.application.entities.User;
import com.application.repositories.MessageGenericRepository;
import com.application.repositories.UserGenericRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessageTests extends Definition {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserGenericRepository userRepository;

    @Autowired
    private MessageGenericRepository messageRepository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(messageRepository).isNotNull();
    }

    @Test
    public void getMessageApiTest() throws Exception {
        User userFrom = userRepository.save(new User(null, "From", "from@chat.com"));
        User userTo = userRepository.save(new User(null, "To", "to@chat.com"));
        Message message = messageRepository.save(new Message(null,
                "chat01",
                Instant.parse("2020-05-04T23:38:00.208Z"),
                userFrom.getId(),
                userTo.getId(),
                "message 01"));

        mockMvc.perform(get("/messages/"+message.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.conversationId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.conversationId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.from").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.from").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.to").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.to").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").isNotEmpty());
    }

    @Test
    public void postMessageApiTest() throws Exception {
        User userFrom = userRepository.save(new User(null, "From2", "from2@chat.com"));
        User userTo = userRepository.save(new User(null, "To2", "to2@chat.com"));
        MessageRequestDto dto = new MessageRequestDto("chat02",
                "2020-05-04T23:40:00.208Z",
                userFrom.getId(),
                userTo.getId(),
                "message 02");

        ResultActions resultActions = mockMvc.perform( MockMvcRequestBuilders
                .post("/messages")
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.conversationId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.conversationId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.from").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.from").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.to").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.to").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").isNotEmpty());


        resultActions.andDo(MockMvcResultHandlers.print());

    }

}
