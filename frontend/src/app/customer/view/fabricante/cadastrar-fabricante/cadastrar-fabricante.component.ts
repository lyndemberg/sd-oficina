import { Component, OnInit } from '@angular/core';
import { Fabricante } from 'src/app/model/fabricante';
import { FabricanteService } from 'src/app/customer/service/fabricante/fabricante.service';

@Component({
  selector: 'app-cadastrar-fabricante',
  templateUrl: './cadastrar-fabricante.component.html',
  styleUrls: ['./cadastrar-fabricante.component.css']
})
export class CadastrarFabricanteComponent implements OnInit {

  fabricante : Fabricante = {
    nome : ''
  }

  constructor(private fabricanteService : FabricanteService) { }

  ngOnInit() {
  }

  salvar() {
    this.fabricanteService.salvar(this.fabricante).subscribe(res => {
      console.log("salvando");
      if (res.status == 200) {
        alert("Fabricante cadastrado!");
      }
    });
  }
}
