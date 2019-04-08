import { Component, OnInit } from '@angular/core';
import { Cor } from 'src/app/customer/model/cor';

@Component({
  selector: 'app-listar-cor',
  templateUrl: './listar-cor.component.html',
  styleUrls: ['./listar-cor.component.css']
})
export class ListarCorComponent implements OnInit {

  cor : Cor = {
    nome : ''
  }

  cores : Cor[] = [];

  constructor() { }

  ngOnInit() {
  }

}
