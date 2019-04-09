import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Estoque } from '../model/estoque';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class EstoqueService {

    private url: string = environment.API_URL + 'estoque/';

    constructor(public http: HttpClient) { }

    salvar(estoque: Estoque): Observable<HttpResponse<Estoque>> {
        return this.http.post<Estoque>(this.url, estoque, { observe: 'response' });
    }

    buscar(id: number): Observable<HttpResponse<Estoque>> {
        return this.http.get(this.url + id, { observe: 'response' });
    }

    buscarTodos(): Observable<HttpResponse<Estoque[]>> {
        return this.http.get<Estoque[]>(this.url, { observe: 'response' });
    }

    deletar(id: number): Observable<HttpResponse<Estoque>> {
        return this.http.delete(this.url + id, { observe: 'response' });
    }

    atualizar(estoque: Estoque): Observable<HttpResponse<Estoque>> {
        return this.http.put(this.url, estoque, { observe: 'response' });
    }
}
