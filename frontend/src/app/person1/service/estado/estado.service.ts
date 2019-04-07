import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estado } from 'src/app/person1/model/estado.model';

@Injectable({
  providedIn: 'root'
})

export class EstadoService {

  constructor(private http: HttpClient) { }

  salvar(Estado): Observable<Estado> {
    return this.http.post<Estado>('//localhost:8080/api/estado', Estado);
  }

  atualizar(Estado): Observable<Estado> {
    return this.http.put<Estado>('//localhost:8080/api/estado', Estado);
  }

  deletar(id): Observable<any> {
    return this.http.delete<any>('//localhost:8080/api/estado/' + id);
  }

  listar(): Observable<Estado[]> {
    return this.http.get<Estado[]>('//localhost:8080/api/estado');
  }
}
