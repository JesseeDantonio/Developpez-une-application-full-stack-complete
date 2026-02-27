package com.openclassrooms.mddapi.dto.in;

import lombok.Data;

@Data
public class CreateCommentDTO {
    private String content;
    private Long articleId;
    private Long userId;
    private String createdAt;
}
