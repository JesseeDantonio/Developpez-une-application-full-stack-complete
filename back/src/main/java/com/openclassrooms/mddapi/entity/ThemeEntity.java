package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class ThemeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;

    @ManyToMany(mappedBy = "themes") // "themes" est le nom de la variable dans Article
    private Set<ArticleEntity> articles = new HashSet<>();
}
