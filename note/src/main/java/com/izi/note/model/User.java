package com.izi.note.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("users")
@Data
public class User {

    @Id
    private String id;
    private String login;
    private String password;
    private List<String> roles;
}
