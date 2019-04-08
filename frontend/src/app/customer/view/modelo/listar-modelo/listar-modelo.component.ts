import { Component, OnInit } from '@angular/core';
import { Modelo } from 'src/app/customer/model/modelo';

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

  constructor() { }

  ngOnInit() {
  }

}
