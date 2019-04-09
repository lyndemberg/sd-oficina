import { Component, OnInit } from '@angular/core';
import { Modelo } from 'src/app/customer/model/modelo';

@Component({
  selector: 'app-cadastrar-modelo',
  templateUrl: './cadastrar-modelo.component.html',
  styleUrls: ['./cadastrar-modelo.component.css']
})
export class CadastrarModeloComponent implements OnInit {

  modelo : Modelo = {
    fabricante : null,
    nome : '',
    tipo : ''
  }

  constructor() { }

  ngOnInit() {
  }

}
