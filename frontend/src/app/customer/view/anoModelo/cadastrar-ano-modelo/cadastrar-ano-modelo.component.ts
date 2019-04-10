import { Component, OnInit } from '@angular/core';
import { AnoModelo } from 'src/app/customer/model/anoModelo';
import { AnoModeloService } from 'src/app/customer/service/anoModelo/ano-modelo.service';

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

  constructor( private anoModeloService : AnoModeloService) { }

  ngOnInit() {
  }

  salvar() {
    this.anoModeloService.salvar(this.anoModelo).subscribe(res => {
      console.log("salvando");
      if (res.status == 200) {
        alert("Ano Modelo cadastrado!");
      }
    });
  }
}
