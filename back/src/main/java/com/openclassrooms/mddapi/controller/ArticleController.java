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

    // GET /api/articles/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Integer id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        return ResponseEntity.ok(articleDTO);
    }

    // GET /api/articles
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    // POST /api/articles
    @PostMapping
    public ResponseEntity<CreateArticleDTO> createArticle(@RequestBody CreateArticleDTO articleDto) {
        CreateArticleDTO createdArticle = articleService.createArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }

    // PUT /api/articles/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Integer id, @RequestBody CreateArticleDTO articleDto) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleDto);
        return ResponseEntity.ok(updatedArticle);
    }

    // DELETE /api/articles/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
