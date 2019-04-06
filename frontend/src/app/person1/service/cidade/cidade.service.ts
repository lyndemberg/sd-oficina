import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cidade } from '../../model/cidade.model';

@Injectable({
  providedIn: 'root'
})
export class CidadeService {

  constructor(private http: HttpClient) { }

  salvar(Cidade): Observable<Cidade> {
    return this.http.post<Cidade>('//localhost:8080/api/cidade', Cidade);
  }

  atualizar(Cidade): Observable<Cidade> {
    return this.http.put<Cidade>('//localhost:8080/api/cidade', Cidade);
  }

  deletar(id): Observable<any> {
    return this.http.delete<any>('//localhost:8080/api/cidade/' + id);
  }

  listar(): Observable<Cidade[]> {
    return this.http.get<Cidade[]>('//localhost:8080/api/cidade');
  }
}
