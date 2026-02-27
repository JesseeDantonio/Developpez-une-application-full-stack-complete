package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;

    @ManyToMany
    @JoinTable(
            name = "article_theme",               // Nom de ta table de liaison SQL
            joinColumns = @JoinColumn(name = "article_id"), // Colonne qui lie à Article
            inverseJoinColumns = @JoinColumn(name = "theme_id") // Colonne qui lie à Theme
    )
    private Set<ThemeEntity> themes = new HashSet<>();
}
