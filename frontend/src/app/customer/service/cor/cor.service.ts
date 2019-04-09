import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cor } from '../../model/cor';

@Injectable({
  providedIn: 'root'
})
export class CorService {

  constructor(private http: HttpClient) { }

  salvar(Cor): Observable<Cor> {
    return this.http.post<Cor>('https://localhost:8080/api/cor', Cor);
  }

  atualizar(Cor): Observable<Cor> {
    return this.http.put<Cor>('https://localhost:8080/api/cor', Cor);
  }

  deletar(id): Observable<any> {
    return this.http.delete<any>('https://localhost:8080/api/cor/' + id);
  }

  listarTodos(): Observable<Cor[]> {
    return this.http.get<Cor[]>('https://localhost:8080/api/cor');
  }

  listarPorId(id): Observable<Cor[]> {
    return this.http.get<Cor[]>('https://localhost:8080/api/cor/' + id);
  }
}
