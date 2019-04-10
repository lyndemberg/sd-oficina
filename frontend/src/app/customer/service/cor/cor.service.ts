import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cor } from '../../model/cor';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class CorService {

  private url: string = environment.API_URL + 'cor/';
  constructor(private http: HttpClient) { }

  salvar(cor : Cor): Observable<HttpResponse<Cor>> {
    return this.http.post<Cor>( this.url , cor, { observe: 'response' });
  }

  atualizar(cor : Cor): Observable<HttpResponse<Cor>> {
    return this.http.put<Cor>( this.url , cor, { observe: 'response' });
  }

  deletar(id : number): Observable<HttpResponse<Cor>> {
    return this.http.delete<any>( this.url + id, { observe: 'response' });
  }

  listarTodos():Observable<HttpResponse<Cor[]>> {
    return this.http.get<Cor[]>( this.url, { observe: 'response' });
  }

  listarPorId(id : number): Observable<HttpResponse<Cor>> {
    return this.http.get<Cor>( this.url + id, { observe: 'response' } );
  }
}
