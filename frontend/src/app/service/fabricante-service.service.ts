import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Fabricante } from '../model/fabricante';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FabricanteServiceService {

  private url: string = environment.API_URL + "fabricante/";

  constructor(private http: HttpClient) { }


  salvar(fabricante: Fabricante): Observable<HttpResponse<Fabricante>> {
    return this.http.post<Fabricante>(this.url, fabricante, { observe: 'response' });
  }

  buscarTodos(): Observable<HttpResponse<Fabricante[]>> {
    return this.http.get<Fabricante[]>(this.url, { observe: 'response' });
  }

  buscar(id: number): Observable<HttpResponse<Fabricante>> {
    return this.http.get<Fabricante>(this.url + id, { observe: 'response' });
  }

  atualizar(fabricante: Fabricante): Observable<HttpResponse<Fabricante>> {
    return this.http.put<Fabricante>(this.url, fabricante, { observe: 'response' });
  }

  deletar(id: number): Observable<HttpResponse<Object>> {
    return this.http.delete(this.url, { observe: 'response' });
  }
}
