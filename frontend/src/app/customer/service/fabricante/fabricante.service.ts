import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Fabricante } from 'src/app/model/fabricante';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FabricanteService {

  private url: string = environment.API_URL + 'fabricante/';
  constructor(private http: HttpClient) { }

  salvar(fabricante : Fabricante): Observable<HttpResponse<Fabricante>> {
    return this.http.post<Fabricante>( this.url , fabricante, { observe: 'response' });
  }

  atualizar(fabricante : Fabricante): Observable<HttpResponse<Fabricante>> {
    return this.http.put<Fabricante>( this.url , fabricante, { observe: 'response' });
  }

  deletar(id : number): Observable<HttpResponse<Fabricante>> {
    return this.http.delete<any>( this.url + id, { observe: 'response' });
  }

  listarTodos(): Observable<HttpResponse<Fabricante[]>> {
    return this.http.get<Fabricante[]>( this.url, { observe: 'response' });
  }

  listarPorId(id : number): Observable<HttpResponse<Fabricante>> {
    return this.http.get<Fabricante>( this.url + id, { observe: 'response' } );
  }
}
