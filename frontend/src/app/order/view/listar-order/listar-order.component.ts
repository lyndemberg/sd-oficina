import { Component, OnInit } from '@angular/core';
import { OrdemServico } from '../../model/ordemServico';
import { Cliente } from 'src/app/person/model/cliente.model';
import { ClienteService } from 'src/app/person/service/cliente/cliente.service';
import { OrderService } from '../../service/order.service';

@Component({
  selector: 'app-listar-order',
  templateUrl: './listar-order.component.html',
  styleUrls: ['./listar-order.component.css']
})
export class ListarOrderComponent implements OnInit {

  ordens: OrdemServico[] = [];
  buscar: any;
  clientes: Cliente[] = [];

  constructor(private clienteService: ClienteService,
    private orderService: OrderService) { }

  ngOnInit() {
    //this.getClientes();
  }

  getClientes() {
    this.clienteService.listar().subscribe(
      res => {
        this.clientes = res.body;
      }
    )
  }

  listarPorClinte(){
    this.orderService.listarPorCliente(this.buscar.id).subscribe(
      res => {
        this.ordens = res.body;
      }
    )
  }

  realizarPagamento(ordemValue) {
    this.orderService.realizarPagamento(ordemValue).subscribe(
      res => {
        console.log("pagando");
        if (res.status == 200) {
          alert("Ordem Paga!");
        }
      }
    )
  }

  concluirOrdem(ordemValue) {
    this.orderService.concluirOrdem(ordemValue).subscribe(
      res => {
        console.log("concluindo");
        if (res.status == 200) {
          alert("Ordem Conluida!");
        }
      }
    )
  }
}
