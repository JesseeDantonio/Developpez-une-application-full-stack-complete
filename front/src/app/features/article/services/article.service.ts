// features/article/services/article.service.ts
import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/core/services/api.service';
import { ArticleDto } from './../../../core/dto/in/articleDto'; // Ou ton DTO
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root' // 'root' le rend dispo partout, c'est OK pour un singleton
})
export class ArticleService extends ApiService<ArticleDto> {
  constructor(http: HttpClient) {
    super(http, 'api/articles'); // Juste le endpoint 'articles'
  }
  
  // Méthodes spécifiques aux articles UNIQUEMENT ici
}