import { Component, OnInit } from '@angular/core';
import { EstadoService } from 'src/app/person1/service/estado.service';
import { Estado } from 'src/app/person1/model/estado.model';

@Component({
  selector: 'app-cadastrar-estado',
  templateUrl: './cadastrar-estado.component.html',
  styleUrls: ['./cadastrar-estado.component.css']
})

export class CadastrarEstadoComponent implements OnInit {

  estado: Estado;
  messageSuccess: boolean;
  messageError: boolean;

  constructor(private estadoService: EstadoService) { }

  ngOnInit() {
    this.estado = new Estado();
  }

  salvar(estado) {
    if(estado.nome == null || estado.nome == ""){
      alert("Por favor preencha todos os campos!")
    } else {
    this.estadoService.salvar(estado).subscribe(data => {
      if(data != null) {
        this.messageSuccess = true;
      } else {
        this.messageError = true;
      }
    });
  }
  }

}
