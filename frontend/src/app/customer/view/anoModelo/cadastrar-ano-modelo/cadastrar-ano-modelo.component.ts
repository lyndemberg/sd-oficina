import { Component, OnInit } from '@angular/core';
import { AnoModelo } from 'src/app/customer/model/anoModelo';

@Component({
  selector: 'app-cadastrar-ano-modelo',
  templateUrl: './cadastrar-ano-modelo.component.html',
  styleUrls: ['./cadastrar-ano-modelo.component.css']
})
export class CadastrarAnoModeloComponent implements OnInit {

  anoModelo : AnoModelo = {
    nome : '',
    modelo : null,
    tipo : '',
    valor : 0
  }

  constructor() { }

  ngOnInit() {
  }

}
