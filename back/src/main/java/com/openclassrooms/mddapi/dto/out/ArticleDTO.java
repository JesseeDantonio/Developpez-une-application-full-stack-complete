package com.openclassrooms.mddapi.dto.out;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String createdAt;
    private String updatedAt;

    private List<Integer> themeIds = new ArrayList<>();
}
