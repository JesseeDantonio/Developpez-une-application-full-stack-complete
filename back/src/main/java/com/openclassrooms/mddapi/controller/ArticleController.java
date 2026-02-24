package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.in.CreateArticleDTO;
import com.openclassrooms.mddapi.dto.out.ArticleDTO;
import com.openclassrooms.mddapi.service.ArticleService;
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
    public ArticleDTO getArticleById(@PathVariable Integer id) {
        return articleService.getArticleById(id);
    }

    // GET /api/articles
    @GetMapping
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles();
    }

    // POST /api/articles
    @PostMapping
    public CreateArticleDTO createArticle(@RequestBody CreateArticleDTO articleDto) {
        return articleService.createArticle(articleDto);
    }

    // PUT /api/articles/{id}
    @PutMapping("/{id}")
    public ArticleDTO updateArticle(@PathVariable Integer id, @RequestBody CreateArticleDTO articleDto) {
        return articleService.updateArticle(id, articleDto);
    }

    // DELETE /api/articles/{id}
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Integer id) {
        articleService.deleteArticle(id);
    }
}
