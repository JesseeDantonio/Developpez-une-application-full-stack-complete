import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private apiUrl = '/api/auth'; 

  constructor(private http: HttpClient, private router: Router) { }

  // Méthode de connexion
  login(credentials: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        // On suppose que le backend renvoie un objet { token: "..." }
        if (response && response.token) {
          this.setToken(response.token);
        }
      })
    );
  }

  // Méthode d'inscription
  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData);
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