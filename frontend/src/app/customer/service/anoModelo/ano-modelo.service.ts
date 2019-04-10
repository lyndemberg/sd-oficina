import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnoModelo } from '../../model/anoModelo';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnoModeloService {

  private url: string = environment.API_URL + 'anoModelo/';
  constructor(private http: HttpClient) { }

  salvar(anoModelo : AnoModelo): Observable<HttpResponse<AnoModelo>> {
    return this.http.post<AnoModelo>( this.url , anoModelo, { observe: 'response' });
  }

  atualizar( anoModelo : AnoModelo): Observable<HttpResponse<AnoModelo>> {
    return this.http.put<AnoModelo>( this.url , anoModelo, { observe: 'response' });
  }

  deletar(id : number): Observable<HttpResponse<AnoModelo>> {
    return this.http.delete<any>( this.url + id, { observe: 'response' });
  }

  listarTodos():Observable<HttpResponse<AnoModelo[]>> {
    return this.http.get<AnoModelo[]>( this.url, { observe: 'response' });
  }

  listarPorId(id : number): Observable<HttpResponse<AnoModelo>> {
    return this.http.get<AnoModelo>( this.url + id, { observe: 'response' } );
  }
}
