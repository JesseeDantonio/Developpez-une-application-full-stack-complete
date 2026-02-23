package com.openclassrooms.mddapi.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ThemeDTO {
    private Long id;
    private String name;
    private String description;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
}
