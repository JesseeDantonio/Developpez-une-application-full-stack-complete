package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.in.CreateArticleDTO;
import com.openclassrooms.mddapi.dto.out.ArticleDTO;
import com.openclassrooms.mddapi.entity.ArticleEntity;
import com.openclassrooms.mddapi.entity.ThemeEntity;
import com.openclassrooms.mddapi.entity.UserEntity;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, ThemeRepository themeRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
    }

    public List<ArticleDTO> getAllArticles() {
        List<ArticleDTO> articles = new ArrayList<>();
        articleRepository.findAll().forEach(article -> {
            ArticleDTO dto = toDTO(article);
            articles.add(dto);
        });

        return articles;
    }

    public ArticleDTO getArticleById(Integer id) {
        return toDTO(articleRepository.findById(id).orElseThrow());
    }

    public CreateArticleDTO createArticle(CreateArticleDTO articleDto) {
        articleDto.setCreatedAt(LocalDate.now().toString());
        articleDto.setUpdatedAt(LocalDate.now().toString());
        articleRepository.save(toEntity(articleDto));
        return articleDto;
    }

    public ArticleDTO updateArticle(Integer id, CreateArticleDTO articleDto) {
        ArticleEntity existingArticle = articleRepository.findById(id).orElse(null);
        if (existingArticle != null) {
            if (articleDto.getTitle() != null && !articleDto.getTitle().isEmpty()) existingArticle.setTitle(articleDto.getTitle());
            if (articleDto.getContent() != null && !articleDto.getContent().isEmpty()) existingArticle.setContent(articleDto.getContent());
            if (articleDto.getThemeIds() != null && !articleDto.getThemeIds().isEmpty()) {
                existingArticle.setThemes(new HashSet<>(themeRepository.findAllById(articleDto.getThemeIds())));
            }
            existingArticle.setUpdatedAt(LocalDate.now().toString());
            articleRepository.save(existingArticle);
            return toDTO(existingArticle);
        } else {
            return null;
        }
    }

    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }

    private ArticleEntity toEntity(CreateArticleDTO dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        UserEntity owner = userRepository.findById(dto.getUserId().intValue())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        entity.setUser(owner);

        if (dto.getThemeIds() != null && !dto.getThemeIds().isEmpty()) {
            // On récupère les vrais objets Theme depuis la BDD à partir des IDs envoyés
            List<ThemeEntity> themes = themeRepository.findAllById(dto.getThemeIds());
            // On les ajoute à l'entité
            entity.setThemes(new HashSet<>(themes));
        }

        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setUserId(entity.getUser().getId());
        dto.setThemeIds(
                entity.getThemes().stream()
                        .map(theme -> theme.getId().intValue()) // Convertit le Long en int
                        .toList()
        );
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
