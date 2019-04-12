import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Servico } from '../model/servico';

@Injectable({
  providedIn: 'root'
})
export class ServicoService {

  private url: string = environment.API_URL + 'servico/';

  constructor(public http: HttpClient) { }

  buscarTodos(): Observable<HttpResponse<Servico[]>> {
    return this.http.get<Servico[]>(this.url, { observe: 'response' });
}
}
