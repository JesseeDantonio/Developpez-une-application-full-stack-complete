package com.openclassrooms.mddapi.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateArticleDTO {
    private String title;
    private String content;
    private Long userId;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;

    private List<Integer> themeIds;
}
