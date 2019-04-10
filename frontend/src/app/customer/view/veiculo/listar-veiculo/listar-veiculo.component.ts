import { Component, OnInit } from '@angular/core';
import { Veiculo } from 'src/app/customer/model/veiculo';
import { VeiculoService } from 'src/app/customer/service/veiculo/veiculo.service';

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

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(private veiculoService : VeiculoService) { }

  ngOnInit() {
    this.getAll();
  }

  showDialogUpdate(veiculo) {
    this.veiculo = veiculo;
    this.displayUpdate = true;
  }

  showDialogDelete(veiculo) {
    this.veiculo = veiculo;
    this.displayDelete = true;
  }

  atualizar(veiculo: Veiculo) {
    this.veiculoService.atualizar(veiculo).subscribe(res => {
      alert("Veiculo atualizado!");
    })
  }

  deletar(veiculo: Veiculo) {
    this.veiculoService.deletar(veiculo.id).subscribe(res => {
      if (res.status == 200) {
        alert("Veiculo deletado!");
      }
    });
  }

  getAll() {
    this.veiculoService.listarTodos().subscribe(res => {
      console.log(res.body.toString());
      this.veiculos = res.body;
    });
  }
}
