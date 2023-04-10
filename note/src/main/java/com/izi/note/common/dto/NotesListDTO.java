package com.izi.note.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.izi.note.model.Note;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NotesListDTO implements Serializable {

    @JsonProperty("notes")
    @JsonInclude(NON_NULL)
    private List<Note> notes;
}
