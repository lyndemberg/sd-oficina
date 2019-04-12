import { Component, OnInit } from '@angular/core';
import { OrdemServico } from '../../model/ordemServico';

@Component({
  selector: 'app-cadastro-order',
  templateUrl: './cadastro-order.component.html',
  styleUrls: ['./cadastro-order.component.css']
})
export class CadastroOrderComponent implements OnInit {

  ordem: OrdemServico = {
    cliente: null,
    concluida: null,
    dataPagamento: null,
    dataRegistro: null,
    pago: null,
    servicos: null,
    veiculo: null
  }

  constructor() { }

  ngOnInit() {
  }

}
