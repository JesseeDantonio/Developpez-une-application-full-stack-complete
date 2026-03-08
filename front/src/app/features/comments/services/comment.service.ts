// features/article/services/article.service.ts
import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/core/services/api.service';
import { HttpClient } from '@angular/common/http';
import { CommentDto } from 'src/app/core/dto/in/CommentDto';

@Injectable({
  providedIn: 'root' // 'root' le rend dispo partout, c'est OK pour un singleton
})
export class CommentService extends ApiService<CommentDto> {
  constructor(http: HttpClient) {
    super(http, 'api/comments'); // Juste le endpoint 'comments'
  }
  
  // Méthodes spécifiques aux commentaires UNIQUEMENT ici
}