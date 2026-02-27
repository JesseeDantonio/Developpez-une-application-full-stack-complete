package com.openclassrooms.mddapi.dto.out;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private Long articleId;
    private Long userId;
    private String createdAt;
}
