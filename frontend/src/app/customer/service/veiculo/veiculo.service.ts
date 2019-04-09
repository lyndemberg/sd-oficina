import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Veiculo } from '../../model/veiculo';

@Injectable({
  providedIn: 'root'
})
export class VeiculoService {

  constructor(private http: HttpClient) { }

  salvar(Veiculo): Observable<Veiculo> {
    return this.http.post<Veiculo>('https://localhost:8080/api/veiculo', Veiculo);
  }

  atualizar(Veiculo): Observable<Veiculo> {
    return this.http.put<Veiculo>('https://localhost:8080/api/veiculo', Veiculo);
  }

  deletar(id): Observable<any> {
    return this.http.delete<any>('https://localhost:8080/api/veiculo/' + id);
  }

  listarTodos(): Observable<Veiculo[]> {
    return this.http.get<Veiculo[]>('https://localhost:8080/api/veiculo');
  }

  listarPorId(id): Observable<Veiculo[]> {
    return this.http.get<Veiculo[]>('https://localhost:8080/api/veiculo/' + id);
  }
}
