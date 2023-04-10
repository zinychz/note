package com.izi.note.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LikeDTO {
    @JsonProperty(value = "note", required = true)
    private String noteId;
    @JsonProperty(value = "user", required = true)
    private String userId;
}
