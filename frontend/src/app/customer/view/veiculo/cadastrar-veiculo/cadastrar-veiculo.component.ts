import { Component, OnInit } from '@angular/core';
import { Veiculo } from 'src/app/customer/model/veiculo';

@Component({
  selector: 'app-cadastrar-veiculo',
  templateUrl: './cadastrar-veiculo.component.html',
  styleUrls: ['./cadastrar-veiculo.component.css']
})
export class CadastrarVeiculoComponent implements OnInit {

  veiculo : Veiculo = {
    cor : null,
    modelo : null,
    placa : '',
    quilometragem : 0
  }

  constructor() { }

  ngOnInit() {
  }

}
