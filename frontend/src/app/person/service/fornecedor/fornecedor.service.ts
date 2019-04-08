import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Fornecedor } from '../../model/fornecedor.model';

@Injectable({
  providedIn: 'root'
})
export class FornecedorService {

  constructor(private http: HttpClient) { }

  salvar(Fornecedor): Observable<Fornecedor> {
    return this.http.post<Fornecedor>('//localhost:8080/api/fornecedor', Fornecedor);
  }

  atualizar(Fornecedor): Observable<Fornecedor> {
    return this.http.put<Fornecedor>('//localhost:8080/api/fornecedor', Fornecedor);
  }

  deletar(id): Observable<any> {
    return this.http.delete<any>('//localhost:8080/api/fornecedor/' + id);
  }

  listar(): Observable<Fornecedor[]> {
    return this.http.get<Fornecedor[]>('//localhost:8080/api/fornecedor');
  }
}
