// core/services/api.service.ts
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Resource } from "./../models/ressource.interface"

// On utilise <T> pour dire "Ce service marche avec n'importe quel type T"
export abstract class ApiService<T extends Resource> {
  
  constructor(
    protected http: HttpClient,
    protected actionUrl: string
  ) {}

  // CREATE
  // Note: Omit<T, 'id'> signifie "Prends le type T mais sans la propriété 'id'", car généralement on ne fournit pas l'id lors de la création
  // Cependant, pour simplifier, on peut aussi utiliser Partial<T> qui rend toutes les propriétés optionnelles, y compris l'id
  create(resource: Partial<T>): Observable<T> {
    return this.http.post<T>(this.actionUrl, resource);
  }

  // READ (All)
  getAll(): Observable<T[]> {
    return this.http.get<T[]>(this.actionUrl);
  }

  // READ (One)
  getById(id: number): Observable<T> {
    return this.http.get<T>(`${this.actionUrl}/${id}`);
  }

  // UPDATE
  update(resource: T): Observable<T> {
    return this.http.put<T>(`${this.actionUrl}/${resource.id}`, resource);
  }

  // DELETE
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.actionUrl}/${id}`);
  }
}