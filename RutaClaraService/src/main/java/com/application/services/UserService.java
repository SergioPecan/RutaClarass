package com.application.services;

import com.application.dtos.UserRequestDto;
import com.application.entities.User;
import com.application.repositories.UserGenericRepository;
import com.application.services.exceptions.ObjectNotFoundException;
import com.application.services.exceptions.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserGenericRepository userGenericRepository;
    

    public List<User> getAll(String name, String email) {
        if(name != null || email != null) {
            return userGenericRepository.findByNameOrEmailRegex(name, email);
        } else {
            return userGenericRepository.findAll();
        }
    }

    public User findById(String id) {
        Optional<User> user = userGenericRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("user not found: "+id));
    }

    public User insert(UserRequestDto dto) {
        validations(dto);
        return userGenericRepository.insert(dto.toUser());
    }

    public User updateById(String id, UserRequestDto dto) {
        User user = findById(id);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return userGenericRepository.save(user);
    }

    public void deleteById(String id) {
        User user = findById(id);
        userGenericRepository.delete(user);
    }

    private void validations(UserRequestDto dto) {
        if(userGenericRepository.findFirstByEmail(dto.getEmail()) != null) {
            throw new ValidateException("E-mail already exists: "+dto.getEmail());
        }
    }
}
