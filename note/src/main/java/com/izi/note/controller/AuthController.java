package com.izi.note.controller;

import com.izi.note.common.Constants;
import com.izi.note.common.dto.AuthRequestDTO;
import com.izi.note.model.User;
import com.izi.note.security.jwt.JwtTokenProvider;
import com.izi.note.service.UserMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(Constants.URL.NOTES_CONTROLLER_ROOT_URL)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMongoService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserMongoService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping(value = Constants.URL.LOGIN)
    public ResponseEntity login(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            String login = authRequestDTO.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, authRequestDTO.getPassword()));

            User user = userService.findUserByLogin(login);
            if (user == null) {
                throw new UsernameNotFoundException("User with login " + login + " not found");
            }
            String token = jwtTokenProvider.createToken(login, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", login);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }

    @PostMapping(value = Constants.URL.REGISTER)
    public ResponseEntity register(@RequestBody AuthRequestDTO authRequestDTO) {
        userService.registerUser(authRequestDTO);
        return ResponseEntity.ok().build();
    }
}
