package com.izi.note.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContentDTO {

    @JsonProperty(value = "content", required = true)
    private String content;
}
