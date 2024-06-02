package com.application.repositories;

import com.application.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGenericRepository extends MongoRepository<User, String> {
    User findFirstByEmail(String email);

    List<User> findByNameContaining(String text);

    List<User> findByNameContainingIgnoreCase(String text);

    @Query("{'name': { $regex: ?0, $options: 'i' }}")
    List<User> findByNameRegex(String name);

    @Query("{$or: [ {'name': { $regex: ?0, $options: 'i' }}, {'email': { $regex: ?0, $options: 'i' }} ] }")
    List<User> findByNameOrEmailRegex(String name, String email);
}
