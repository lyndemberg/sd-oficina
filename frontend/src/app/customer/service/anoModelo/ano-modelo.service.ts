import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnoModelo } from '../../model/anoModelo';

@Injectable({
  providedIn: 'root'
})
export class AnoModeloService {

  constructor(private http: HttpClient) { }

  salvar(AnoModelo): Observable<AnoModelo> {
    return this.http.post<AnoModelo>('https://localhost:8080/api/anoModelo', AnoModelo);
  }

  atualizar(AnoModelo): Observable<AnoModelo> {
    return this.http.put<AnoModelo>('https://localhost:8080/api/anoModelo', AnoModelo);
  }

  deletar(id): Observable<any> {
    return this.http.delete<any>('https://localhost:8080/api/anoModelo/' + id);
  }

  listarTodos(): Observable<AnoModelo[]> {
    return this.http.get<AnoModelo[]>('https://localhost:8080/api/anoModelo');
  }

  listarPorId(id): Observable<AnoModelo[]> {
    return this.http.get<AnoModelo[]>('https://localhost:8080/api/anoModelo/' + id);
  }
}
