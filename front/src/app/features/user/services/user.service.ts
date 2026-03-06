// feature/user/services/user.service.ts
import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/core/services/api.service';
import { UserDto } from 'src/app/core/dto/in/UserDto';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root' // 'root' le rend dispo partout, c'est OK pour un singleton
})
export class UserService extends ApiService<UserDto> {
  constructor(http: HttpClient) {
    super(http, 'api/users'); // Juste le endpoint 'users'
  }

  // Méthodes spécifiques aux utilisateurs UNIQUEMENT ici
}