import { Component, OnInit } from '@angular/core';
import { Fabricante } from 'src/app/model/fabricante';

@Component({
  selector: 'app-cadastrar-fabricante',
  templateUrl: './cadastrar-fabricante.component.html',
  styleUrls: ['./cadastrar-fabricante.component.css']
})
export class CadastrarFabricanteComponent implements OnInit {

  fabricante : Fabricante = {
    nome : ''
  }

  constructor() { }

  ngOnInit() {
  }

}
