package com.izi.note.repository;

import com.izi.note.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMongoRepository extends MongoRepository<User, String> {
    User findByLogin(String login);
}
