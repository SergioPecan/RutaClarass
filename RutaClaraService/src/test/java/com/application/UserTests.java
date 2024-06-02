package com.application;

import com.application.dtos.UserRequestDto;
import com.application.entities.User;
import com.application.repositories.UserGenericRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserTests extends Definition {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserGenericRepository userRepository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void getUserApiTest() throws Exception {
        User user = userRepository.save(new User(null, "ae", "ae@ae.com"));

        mockMvc.perform(get("/users/"+user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());
    }

    @Test
    public void createUserApiTest() throws Exception
    {
        UserRequestDto dto = new UserRequestDto("ae 2", "ae2@ae.com");

        mockMvc.perform( MockMvcRequestBuilders
                .post("/users")
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ae 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ae2@ae.com"));
    }


    @Test
    public void getUsersApiTest() throws Exception {

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].email").isNotEmpty());
    }

    @Test
    public void deleteUserApiTest() throws Exception
    {
        User user = userRepository.findFirstByEmail("ae@ae.com");

        mockMvc.perform( MockMvcRequestBuilders
                .delete("/users/"+user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

}
