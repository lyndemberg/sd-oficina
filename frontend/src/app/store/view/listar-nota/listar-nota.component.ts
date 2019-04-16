import { Component, OnInit } from '@angular/core';
import { Nota } from '../../model/nota';
import { NotaService } from '../../service/nota.service';

@Component({
  selector: 'app-listar-nota',
  templateUrl: './listar-nota.component.html',
  styleUrls: ['./listar-nota.component.css']
})
export class ListarNotaComponent implements OnInit {

  notas: any[];
  nota: Nota;

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(public service: NotaService) { }

  ngOnInit() {
    this.todos();
    this.nota = new Nota();
  }

  showDialogUpdate(nota) {
    this.nota = nota;
    this.displayUpdate = true;
  }

  showDialogDelete(nota) {
    this.nota = nota;
    this.displayDelete = true;
  }

  atualizar(nota: Nota) {
    this.service.atualizar(nota).subscribe(res=>{
      alert("Nota atualizada");
    })
}

deletar(nota: Nota) {
  this.service.deletar(nota.id).subscribe(res => {
    if (res.status == 200) {
      this.todos();
      alert("Nota deletada");
    }
  });
}

todos() {
  this.service.buscarTodos().subscribe(res => {
    this.notas = res.body;
  
  });
}
}
