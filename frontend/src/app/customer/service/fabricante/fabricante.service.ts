import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Fabricante } from 'src/app/model/fabricante';

@Injectable({
  providedIn: 'root'
})
export class FabricanteService {

  constructor(private http: HttpClient) { }

  salvar(Fabricante): Observable<Fabricante> {
    return this.http.post<Fabricante>('https://localhost:8080/api/fabricante', Fabricante);
  }

  atualizar(Fabricante): Observable<Fabricante> {
    return this.http.put<Fabricante>('https://localhost:8080/api/fabricante', Fabricante);
  }

  deletar(id): Observable<any> {
    return this.http.delete<any>('https://localhost:8080/api/fabricante/' + id);
  }

  listarTodos(): Observable<Fabricante[]> {
    return this.http.get<Fabricante[]>('https://localhost:8080/api/fabricante');
  }

  listarPorId(id): Observable<Fabricante[]> {
    return this.http.get<Fabricante[]>('https://localhost:8080/api/fabricante/' + id);
  }
}
