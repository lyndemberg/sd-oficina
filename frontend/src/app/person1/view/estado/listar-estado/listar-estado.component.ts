import { Component, OnInit } from '@angular/core';
import { EstadoService } from 'src/app/person1/service/estado.service';
import { Estado } from 'src/app/person1/model/estado.model';

@Component({
  selector: 'app-listar-estado',
  templateUrl: './listar-estado.component.html',
  styleUrls: ['./listar-estado.component.css']
})
export class ListarEstadoComponent implements OnInit {

  estados: Estado[];

  estado: Estado;

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(private estadoService: EstadoService) { }

  ngOnInit() {
    this.estado = new Estado();
    this.listar();
  }

  listar() {
    this.estadoService.listar().subscribe(data => {
      this.estados = data;
    });
  }

  atualizar(estado) {
    this.estadoService.atualizar(estado).subscribe(data => {
    });
    this.displayUpdate = false;
  }

  deletar(estado) {
    this.estadoService.deletar(estado.id).subscribe(data => {
    });
    this.displayDelete = false;
    location.reload();
  }

  showDialogUpdate(estado) {
    this.estado = estado;
    this.displayUpdate = true;
  }

  showDialogDelete(estado) {
    this.estado = estado;
    this.displayDelete = true;
  }

}
