// features/article/services/article.service.ts
import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/core/services/api.service';
import { HttpClient } from '@angular/common/http';
import { ThemeDto } from 'src/app/core/dto/in/ThemeDto';

@Injectable({
  providedIn: 'root' // 'root' le rend dispo partout, c'est OK pour un singleton
})
export class ThemeService extends ApiService<ThemeDto> {
  constructor(http: HttpClient) {
    super(http, 'api/themes'); // Juste le endpoint 'themes'
  }
  
  // Méthodes spécifiques aux themes UNIQUEMENT ici
}