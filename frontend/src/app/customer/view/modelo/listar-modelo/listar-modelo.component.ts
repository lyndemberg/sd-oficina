import { Component, OnInit } from '@angular/core';
import { Modelo } from 'src/app/customer/model/modelo';
import { ModeloService } from 'src/app/customer/service/modelo/modelo.service';

@Component({
  selector: 'app-listar-modelo',
  templateUrl: './listar-modelo.component.html',
  styleUrls: ['./listar-modelo.component.css']
})
export class ListarModeloComponent implements OnInit {

  modelo : Modelo = {
    fabricante : null,
    nome : '',
    tipo : ''
  }

  modelos : Modelo [] = [];
  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(private modeloService : ModeloService) { }

  ngOnInit() {
    this.getAll();
  }

  showDialogUpdate(fabricante) {
    this.modelo = fabricante;
    this.displayUpdate = true;
  }

  showDialogDelete(fabricante) {
    this.modelo = fabricante;
    this.displayDelete = true;
  }

  atualizar(modelo: Modelo) {
    this.modeloService.atualizar(modelo).subscribe(res => {
      alert("Modelo atualizado!");
    })
  }

  deletar(modelo: Modelo) {
    this.modeloService.deletar(modelo.id).subscribe(res => {
      if (res.status == 200) {
        alert("Modelo deletado!");
      }
    });
  }

  getAll() {
    this.modeloService.listarTodos().subscribe(res => {
      console.log(res.body.toString());
      this.modelos = res.body;
    });
  }

}
