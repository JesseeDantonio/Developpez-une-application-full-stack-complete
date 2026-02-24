package com.openclassrooms.mddapi.dto.out;

import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String createdAt;
    private String updatedAt;
}
