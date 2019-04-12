import { Component, OnInit } from '@angular/core';
import { OrdemServico } from '../../model/ordemServico';
import { OrderService } from '../../service/order.service';

@Component({
  selector: 'app-cadastro-order',
  templateUrl: './cadastro-order.component.html',
  styleUrls: ['./cadastro-order.component.css']
})
export class CadastroOrderComponent implements OnInit {

  ordem: OrdemServico = {
    cliente: null,
    concluida: false,
    dataPagamento: null,
    dataRegistro: null,
    pago: false,
    servicos: null,
    veiculo: null
  }

  constructor(private service : OrderService) { }

  ngOnInit() {
  }

  salvar() : void{
    this.service.salvar(this.ordem).subscribe( res => {
      console.log("salvando");
      if (res.status == 200) {
        alert("Ordem cadastrada!");
      }
    })
  }
}
