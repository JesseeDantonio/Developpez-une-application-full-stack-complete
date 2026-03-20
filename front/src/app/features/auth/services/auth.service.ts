import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import {
  AuthResponse,
  LoginRequest,
  RegisterRequest,
} from '../../../core/models/auth.model';
import { TokenService } from '../../../core/services/token.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = '/api/auth';

  constructor(
    private http: HttpClient,
    private router: Router,
    private tokenService: TokenService
  ) { }

  // Méthode de connexion
  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap((response) => {
          if (response && response.token) {
            this.tokenService.setToken(response.token);
            this.router.navigate(['/article']);
          }
        }),
      );
  }

  // Méthode d'inscription
  register(userData: RegisterRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/register`, userData)
      .pipe(
        tap((response) => {
          if (response && response.token) {
            this.tokenService.setToken(response.token);
            this.router.navigate(['/article']);
          }
        }),
      );
  }

  // Déconnexion
  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
