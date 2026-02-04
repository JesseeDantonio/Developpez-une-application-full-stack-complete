import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private apiUrl = '/api/auth'; 

  constructor(private http: HttpClient, private router: Router) { }

  // Méthode de connexion
  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {

        if (response && response.token) {
          this.setToken(response.token);
        }
      })
    );
  }

  // Méthode d'inscription
  register(userData: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, userData);
  }

  // Stocker le token dans le LocalStorage
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Récupérer le token
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Vérifier si l'utilisateur est connecté (vérification basique)
  isAuthenticated(): boolean {
    const token = this.getToken();
    // on vérifie aussi l'expiration du token ici
    return !!token; 
  }

  // Déconnexion
  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}