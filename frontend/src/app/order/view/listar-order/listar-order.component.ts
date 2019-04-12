import { Component, OnInit } from '@angular/core';
import { OrdemServico } from '../../model/ordemServico';

@Component({
  selector: 'app-listar-order',
  templateUrl: './listar-order.component.html',
  styleUrls: ['./listar-order.component.css']
})
export class ListarOrderComponent implements OnInit {

  ordens : OrdemServico [];
  buscar : any;

  constructor() { }

  ngOnInit() {
  }

}
