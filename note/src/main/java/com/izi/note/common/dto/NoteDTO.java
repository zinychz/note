package com.izi.note.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NoteDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("content")
    @JsonInclude(NON_NULL)
    private String content;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("updated")
    private Date updated;

    @JsonProperty("likes")
    @JsonInclude(NON_NULL)
    private Set<String> likes;

    @JsonProperty("author")
    @JsonInclude(NON_NULL)
    private String authorId;
}
