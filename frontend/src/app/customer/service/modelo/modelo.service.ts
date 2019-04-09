import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Modelo } from '../../model/modelo';

@Injectable({
  providedIn: 'root'
})
export class ModeloService {

  constructor(private http: HttpClient) { }

  salvar(Modelo): Observable<Modelo> {
    return this.http.post<Modelo>('https://localhost:8080/api/modelo', Modelo);
  }

  atualizar(Modelo): Observable<Modelo> {
    return this.http.put<Modelo>('https://localhost:8080/api/modelo', Modelo);
  }

  deletar(id): Observable<any> {
    return this.http.delete<any>('https://localhost:8080/api/modelo/' + id);
  }

  listarTodos(): Observable<Modelo[]> {
    return this.http.get<Modelo[]>('https://localhost:8080/api/modelo');
  }

  listarPorId(id): Observable<Modelo[]> {
    return this.http.get<Modelo[]>('https://localhost:8080/api/modelo/' + id);
  }
}
