import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Veiculo } from '../../model/veiculo';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VeiculoService {

  private url: string = environment.API_URL + 'veiculo/';
  constructor(private http: HttpClient) { }

  salvar(veiculo : Veiculo): Observable<HttpResponse<Veiculo>> {
    return this.http.post<Veiculo>( this.url , veiculo, { observe: 'response' });
  }

  atualizar(veiculo : Veiculo): Observable<HttpResponse<Veiculo>> {
    return this.http.put<Veiculo>( this.url , veiculo, { observe: 'response' });
  }

  deletar(id : number): Observable<HttpResponse<Veiculo>> {
    return this.http.delete<any>( this.url + id, { observe: 'response' });
  }

  listarTodos(): Observable<HttpResponse<Veiculo[]>> {
    return this.http.get<Veiculo[]>( this.url, { observe: 'response' });
  }

  listarPorId(id : number): Observable<HttpResponse<Veiculo>> {
    return this.http.get<Veiculo>( this.url + id, { observe: 'response' } );
  }
}
