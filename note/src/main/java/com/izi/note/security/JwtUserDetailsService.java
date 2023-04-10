package com.izi.note.security;

import com.izi.note.model.User;
import com.izi.note.security.jwt.JwtUser;
import com.izi.note.security.jwt.JwtUserFactory;
import com.izi.note.service.UserMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserMongoService userService;

    @Autowired
    public JwtUserDetailsService(UserMongoService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with login " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("user with login {} successfully loaded", username);

        return jwtUser;
    }
}
