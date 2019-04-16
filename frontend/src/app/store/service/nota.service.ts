import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Nota } from '../model/nota';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotaService {

  private url: string = environment.API_URL + 'nota/';

  constructor(public http: HttpClient) { }

  salvar(nota: Nota): Observable<HttpResponse<Nota>>{
    return this.http.post(this.url, nota, {observe: 'response'});
  }

  atualizar(nota: Nota): Observable<HttpResponse<Nota>> {
    return this.http.put(this.url, nota, { observe: 'response' });
  }

  deletar(id: number): Observable<HttpResponse<Nota>> {
    return this.http.delete(this.url + id, { observe: 'response' });
  }

  buscarTodos(): Observable<HttpResponse<Nota[]>> {
    return this.http.get<Nota[]>(this.url, { observe: 'response' });
  }
}
