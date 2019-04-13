import { Component, OnInit } from '@angular/core';
import { Estoque } from 'src/app/store/model/estoque';
import { EstoqueService } from 'src/app/store/service/EstoqueService';

@Component({
  selector: 'app-listar-estoque',
  templateUrl: './listar-estoque.component.html',
  styleUrls: ['./listar-estoque.component.css']
})
export class ListarEstoqueComponent implements OnInit {

  estoques: any[];
  estoque: Estoque;

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(public service: EstoqueService) {

  }

  ngOnInit() {
    this.todos();
    this.estoque = new Estoque();
  }

  showDialogUpdate(estoque) {
    this.estoque = estoque;
    this.displayUpdate = true;
  }

  showDialogDelete(estoque) {
    this.estoque = estoque;
    this.displayDelete = true;
  }


  atualizar(estoque: Estoque) {
      this.service.atualizar(estoque).subscribe(res=>{
        alert("Estoque atualizado");
      })
  }

  deletar(estoque: Estoque) {
    this.service.deletar(estoque.idPeca).subscribe(res => {
      if (res.status == 200) {
        this.todos();
        alert("Estoque deletado");
      }
    });
  }

  todos() {
    this.service.buscarTodos().subscribe(res => {
      console.log(res.body.toString());
      this.estoques = res.body;
    });
  }
}
