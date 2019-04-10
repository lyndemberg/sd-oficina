import { Component, OnInit } from '@angular/core';
import { Veiculo } from 'src/app/customer/model/veiculo';
import { VeiculoService } from 'src/app/customer/service/veiculo/veiculo.service';

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

  constructor(private veiculoService : VeiculoService) { }

  ngOnInit() {
  }

  salvar() {
    this.veiculoService.salvar(this.veiculo).subscribe(res => {
      console.log("salvando");
      if (res.status == 200) {
        alert("Fabricante cadastrado!");
      }
    });
  }
}
