import { Component, OnInit } from '@angular/core';
import { Fabricante } from 'src/app/model/fabricante';
import { FabricanteService } from 'src/app/customer/service/fabricante/fabricante.service';

@Component({
  selector: 'app-listar-fabricante',
  templateUrl: './listar-fabricante.component.html',
  styleUrls: ['./listar-fabricante.component.css']
})
export class ListarFabricanteComponent implements OnInit {

  fabricante : Fabricante = {
    nome : ''
  }

  fabricantes : any[];

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(private fabricanteService : FabricanteService) { }

  ngOnInit() {
    this.getAll();
  }

  showDialogUpdate(fabricante) {
    this.fabricante = fabricante;
    this.displayUpdate = true;
  }

  showDialogDelete(fabricante) {
    this.fabricante = fabricante;
    this.displayDelete = true;
  }

  atualizar(fabricante: Fabricante) {
    this.fabricanteService.atualizar(fabricante).subscribe(res => {
      alert("Fabricante atualizado!");
    })
  }

  deletar(fabricante: Fabricante) {
    this.fabricanteService.deletar(fabricante.id).subscribe(res => {
      if (res.status == 200) {
        alert("Fabricante deletado!");
      }
    });
  }

  getAll() {
    this.fabricanteService.listarTodos().subscribe(res => {
      console.log(res.body.toString());
      this.fabricantes = res.body;
    });
  }

}
