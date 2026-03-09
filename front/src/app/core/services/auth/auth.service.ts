import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import {
  AuthResponse,
  LoginRequest,
  RegisterRequest,
} from '../../models/auth.model';
import { Payload } from './../../models/payload.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = '/api/auth';

  constructor(
    private http: HttpClient,
    private router: Router,
  ) { }

  // Méthode de connexion
  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap((response) => {
          if (response && response.token) {
            this.setToken(response.token);
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
            this.setToken(response.token);
            this.router.navigate(['/article']);
          }
        }),
      );
  }

  // Stocker le token dans le LocalStorage
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Récupérer le token
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Extraire les informations du token
  getPayloadFromToken(): Payload | null {
    const token: string | null = this.getToken();

    if (!token) {
      return null;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload;
    } catch (error) {
      return null;
    }
  }
  // Vérifier si l'utilisateur est authentifié
  isAuthenticated(): boolean {
    const payload : Payload | null = this.getPayloadFromToken();
    if (payload) {
      const currentTime = Math.floor(Date.now() / 1000);
      return payload.exp > currentTime;
    }
    return false;
  }

  // Déconnexion
  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
