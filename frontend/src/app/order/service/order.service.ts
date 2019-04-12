import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrdemServico } from '../model/ordemServico';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url: string = environment.API_URL + 'ordemservico/';
  constructor(private http: HttpClient) { }

  salvar(ordem : OrdemServico): Observable<HttpResponse<OrdemServico>>{
    return this.http.post<OrdemServico>( this.url , ordem, { observe: 'response' });
  }

  realizarPagamento(ordem : OrdemServico): Observable<HttpResponse<OrdemServico>> {
    ordem.pago = true;
    return this.http.put<OrdemServico>( this.url + "pagamento/", ordem, { observe: 'response' });
  }

  concluirOrdem(ordem : OrdemServico): Observable<HttpResponse<OrdemServico>> {
    ordem.concluida = true;
    return this.http.put<OrdemServico>( this.url + "concluir/", ordem, { observe: 'response' });
  }

  listarPorCliente(id : number): Observable<HttpResponse<OrdemServico[]>>  {
    return this.http.get<OrdemServico[]>( this.url + "cliente/" + id, { observe: 'response' });
  }
}
