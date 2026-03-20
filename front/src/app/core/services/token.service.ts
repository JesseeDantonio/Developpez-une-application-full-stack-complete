import { Injectable } from "@angular/core";
import { Payload } from "../models/payload.model";

@Injectable({
    providedIn: 'root',
})
export class TokenService {
    constructor() { }

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
        const payload: Payload | null = this.getPayloadFromToken();
        if (payload) {
            const currentTime = Math.floor(Date.now() / 1000);
            return payload.exp > currentTime;
        }
        return false;
    }
}