package com.izi.note.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document("notes")
@Data
public class Note {

    @Id
    private String id;
    private String content;
    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date updated;
    @Version
    private Integer version;
    private Set<String> likes;
    private String authorId;
}
