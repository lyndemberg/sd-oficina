import { Component, OnInit } from '@angular/core';
import { Fabricante } from 'src/app/model/fabricante';

@Component({
  selector: 'app-listar-fabricante',
  templateUrl: './listar-fabricante.component.html',
  styleUrls: ['./listar-fabricante.component.css']
})
export class ListarFabricanteComponent implements OnInit {

  fabricante : Fabricante = {
    nome : null
  }

  fabricantes : Fabricante[] = [];

  constructor() { }

  ngOnInit() {
  }

}
