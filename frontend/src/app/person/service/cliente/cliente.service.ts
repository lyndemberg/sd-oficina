import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../../model/cliente.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private http: HttpClient) { }

  salvar(Cliente): Observable<Cliente> {
    return this.http.post<Cliente>('//localhost:8080/api/cliente', Cliente);
  }

  atualizar(Cliente): Observable<Cliente> {
    return this.http.put<Cliente>('//localhost:8080/api/cliente', Cliente);
  }

  deletar(id): Observable<any> {
    return this.http.delete<any>('//localhost:8080/api/cliente/' + id);
  }

  listar(): Observable<HttpResponse<Cliente[]>> {
    return this.http.get<Cliente[]>('//localhost:8080/api/cliente', { observe: 'response' });
  }
}
