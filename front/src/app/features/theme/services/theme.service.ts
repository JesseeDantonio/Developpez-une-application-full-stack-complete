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

  // Méthode pour s'abonner à un thème
  public subscribe(themeId: number) {
    return this.http.post(`api/themes/${themeId}/subscribe`, {});
  }

  // Méthode pour se désabonner d'un thème
  public unsubscribe(themeId: number) {
    return this.http.delete(`api/themes/${themeId}/subscribe`);
  }

  // Méthode pour récuperer les themes auxquels l'utilisateur est abonné
  public getSubscribedThemes(userId: number) {
    return this.http.get<ThemeDto[]>(`api/themes/${userId}/subscribers`);
  }
}