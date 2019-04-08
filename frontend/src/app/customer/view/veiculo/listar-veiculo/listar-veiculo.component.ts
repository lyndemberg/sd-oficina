import { Component, OnInit } from '@angular/core';
import { Veiculo } from 'src/app/customer/model/veiculo';

@Component({
  selector: 'app-listar-veiculo',
  templateUrl: './listar-veiculo.component.html',
  styleUrls: ['./listar-veiculo.component.css']
})
export class ListarVeiculoComponent implements OnInit {

  veiculo : Veiculo = {
    cor : null,
    modelo : null,
    placa : '',
    quilometragem : 0
  }

  veiculos : Veiculo [] = [];

  constructor() { }

  ngOnInit() {
  }

}
