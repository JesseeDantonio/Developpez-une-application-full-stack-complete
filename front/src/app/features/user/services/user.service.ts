// feature/user/services/user.service.ts
import { Injectable } from '@angular/core';
import { ApiService } from 'src/app/core/services/api.service';
import { UserDto } from 'src/app/core/dto/in/UserDto';
import { HttpClient } from '@angular/common/http';
import { RegisterRequest } from 'src/app/core/models/auth.model';
import { TokenService } from 'src/app/core/services/token.service';

@Injectable({
  providedIn: 'root' // 'root' le rend dispo partout, c'est OK pour un singleton
})
export class UserService extends ApiService<UserDto> {
  constructor(http: HttpClient, private tokenService: TokenService) {
    super(http, 'api/users'); // Juste le endpoint 'users'
  }

  public updateProfile(userData: Partial<RegisterRequest>) {

    const payload = this.tokenService.getPayloadFromToken();
    if (payload == null) {
      throw new Error("User not authenticated");
    }

    return this.http.put(`${this.actionUrl}/${payload.sub}`, userData);
  }

  // Méthodes spécifiques aux utilisateurs UNIQUEMENT ici
}