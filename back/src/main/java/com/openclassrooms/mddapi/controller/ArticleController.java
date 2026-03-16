package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.in.CreateArticleDTO;
import com.openclassrooms.mddapi.dto.out.ArticleDTO;
import com.openclassrooms.mddapi.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Récupère un article par son ID.
     * @param id L'ID de l'article
     * @return La réponse HTTP avec les détails de l'article
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Integer id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        return ResponseEntity.ok(articleDTO);
    }

    /**
     * Récupère tous les articles.
     * @return La réponse HTTP avec la liste des articles
     */
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    /**
     * Crée un nouvel article.
     * @param articleDto Les données du nouvel article
     * @return La réponse HTTP avec les détails de l'article créé
     */
    @PostMapping
    public ResponseEntity<CreateArticleDTO> createArticle(@RequestBody CreateArticleDTO articleDto) {
        CreateArticleDTO createdArticle = articleService.createArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }

    /**
     * Met à jour un article existant.
     * @param id L'ID de l'article à mettre à jour
     * @param articleDto Les données de l'article mis à jour
     * @return La réponse HTTP avec les détails de l'article mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Integer id, @RequestBody CreateArticleDTO articleDto) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleDto);
        return ResponseEntity.ok(updatedArticle);
    }

    /**
     * Supprime un article.
     * @param id L'ID de l'article à supprimer
     * @return La réponse HTTP
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
