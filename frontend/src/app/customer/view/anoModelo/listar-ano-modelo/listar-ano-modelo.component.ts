import { Component, OnInit } from '@angular/core';
import { AnoModelo } from 'src/app/customer/model/anoModelo';

@Component({
  selector: 'app-listar-ano-modelo',
  templateUrl: './listar-ano-modelo.component.html',
  styleUrls: ['./listar-ano-modelo.component.css']
})
export class ListarAnoModeloComponent implements OnInit {
  
  anoModelos : AnoModelo[] = [];
  anoModelo : AnoModelo = {
    modelo : null,
    nome : '',
    tipo : '',
    valor : 0
  }

  constructor() { }

  ngOnInit() {
  }

}
