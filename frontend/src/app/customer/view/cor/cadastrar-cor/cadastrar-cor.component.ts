import { Component, OnInit } from '@angular/core';
import { Cor } from 'src/app/customer/model/cor';

@Component({
  selector: 'app-cadastrar-cor',
  templateUrl: './cadastrar-cor.component.html',
  styleUrls: ['./cadastrar-cor.component.css']
})
export class CadastrarCorComponent implements OnInit {

  cor : Cor = {
    nome : ''
  }

  constructor() { }

  ngOnInit() {
  }

}
