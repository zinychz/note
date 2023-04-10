package com.izi.note.service;

import com.izi.note.common.Constants;
import com.izi.note.common.dto.AuthRequestDTO;
import com.izi.note.model.User;
import com.izi.note.repository.UserMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserMongoService {

    private final UserMongoRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserMongoService(UserMongoRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    protected User saveUser(String login, String encryptedPassword, List<String> roles) {

        User user = findUserByLogin(login);
        if (user == null) {
            user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setLogin(login);
            user.setPassword(encryptedPassword);
            user.setRoles(roles);
        }

        User userRegistered = userRepository.save(user);
        log.info("user {} successfully registered", userRegistered.getLogin());

        return userRegistered;
    }

    public User registerUser(AuthRequestDTO authRequestDTO) {
        String login = authRequestDTO.getLogin();
        User user = findUserByLogin(login);
        if (user != null) {
            throw new UserAlreadyExistAuthenticationException("User with login " + login + " already exists");
        }

        List<String> defaultRoles = new ArrayList<>();
        defaultRoles.add("ROLE_" + Constants.ROLE.ROLE_USER);
        return saveUser(login, passwordEncoder.encode(authRequestDTO.getPassword()), defaultRoles);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    protected Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }
}
