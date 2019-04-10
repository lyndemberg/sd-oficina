import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Modelo } from '../../model/modelo';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ModeloService {

  private url: string = environment.API_URL + 'modelo/';
  constructor(private http: HttpClient) { }

  salvar(modelo : Modelo): Observable<HttpResponse<Modelo>> {
    return this.http.post<Modelo>( this.url , modelo, { observe: 'response' });
  }

  atualizar(modelo : Modelo): Observable<HttpResponse<Modelo>> {
    return this.http.put<Modelo>( this.url , modelo, { observe: 'response' });
  }

  deletar(id : number): Observable<HttpResponse<Modelo>> {
    return this.http.delete<any>( this.url + id, { observe: 'response' });
  }

  listarTodos(): Observable<HttpResponse<Modelo[]>> {
    return this.http.get<Modelo[]>( this.url, { observe: 'response' });
  }

  listarPorId(id : number): Observable<HttpResponse<Modelo>> {
    return this.http.get<Modelo>( this.url + id, { observe: 'response' } );
  }
}
