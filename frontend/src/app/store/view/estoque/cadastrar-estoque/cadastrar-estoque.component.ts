import { Component, OnInit } from '@angular/core';
import { Estoque } from 'src/app/store/model/estoque';
import { EstoqueService } from 'src/app/store/service/EstoqueService';

@Component({
  selector: 'app-cadastrar-estoque',
  templateUrl: './cadastrar-estoque.component.html',
  styleUrls: ['./cadastrar-estoque.component.css']
})
export class CadastrarEstoqueComponent implements OnInit {

  private estoque: Estoque;

  constructor(public service: EstoqueService) {
    this.estoque = new Estoque();
  }

  ngOnInit() {
  }

  salvar() {
    this.service.salvar(this.estoque).subscribe(res => {
      console.log("salvando");
      if (res.status == 200) {
        alert("estoque cadastrado");
      }
    });
  }

}
