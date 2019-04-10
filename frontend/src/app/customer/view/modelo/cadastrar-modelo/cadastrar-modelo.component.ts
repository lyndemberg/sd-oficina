import { Component, OnInit } from '@angular/core';
import { Modelo } from 'src/app/customer/model/modelo';
import { ModeloService } from 'src/app/customer/service/modelo/modelo.service';

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

  constructor(private modeloService : ModeloService) { }

  ngOnInit() {
  }

  salvar() {
    this.modeloService.salvar(this.modelo).subscribe(res => {
      console.log("salvando");
      if (res.status == 200) {
        alert("Modelo cadastrado!");
      }
    });
  }
}
