import { Component, OnInit } from '@angular/core';
import { OrdemServico } from '../../model/ordemServico';
import { OrderService } from '../../service/order.service';
import { ClienteService } from 'src/app/person/service/cliente/cliente.service';
import { VeiculoService } from 'src/app/customer/service/veiculo/veiculo.service';
import { Cliente } from 'src/app/person/model/cliente.model';
import { Veiculo } from 'src/app/customer/model/veiculo';
import { Servico } from 'src/app/store/model/servico';
import { ServicoService } from 'src/app/store/service/servico.service';

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

  clientes : Cliente [];
  veiculos : Veiculo [];
  servicos : Servico [];

  constructor(private service : OrderService,
    private clienteService : ClienteService,
    private veiculoService : VeiculoService,
    private servicoService : ServicoService) { }

  ngOnInit() {
    this.getClientes();
    this.getVeiculos();
    this.getServicos();
  }

  salvar() : void{
    this.service.salvar(this.ordem).subscribe( res => {
      console.log("salvando");
      if (res.status == 200) {
        alert("Ordem cadastrada!");
      }
    })
  }

  getClientes(){
    this.clienteService.listar().subscribe( res => {
      this.clientes = res.body;
    })
  }

  getVeiculos(){
    this.veiculoService.listarTodos().subscribe( res => {
      this.veiculos = res.body;
    })
  }

  getServicos(){
    this.servicoService.buscarTodos().subscribe( res => {
      this.servicos = res.body;
    })
  }
}
