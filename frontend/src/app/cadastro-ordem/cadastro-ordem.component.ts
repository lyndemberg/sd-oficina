import { Component, OnInit } from '@angular/core';
import { Cliente } from '../person/model/cliente.model';
import { Veiculo } from '../customer/model/veiculo';
import { ClienteService } from '../person/service/cliente/cliente.service';

@Component({
  selector: 'app-cadastro-ordem',
  templateUrl: './cadastro-ordem.component.html',
  styleUrls: ['./cadastro-ordem.component.css']
})
export class CadastroOrdemComponent implements OnInit {

  clientes: Cliente[];
  veiculos: Veiculo[];
  
  constructor(private clienteService: ClienteService) { }

  ngOnInit() {
  }

}
