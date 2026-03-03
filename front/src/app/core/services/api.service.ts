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
  create(resource: T): Observable<T> {
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