import { Component, OnInit } from '@angular/core';
import { Servico } from 'src/app/store/model/servico';
import { ServicoService } from 'src/app/store/service/servico.service';

@Component({
  selector: 'app-cadastrar-servico',
  templateUrl: './cadastrar-servico.component.html',
  styleUrls: ['./cadastrar-servico.component.css']
})
export class CadastrarServicoComponent implements OnInit {

  private servico: Servico = new Servico();

  constructor(public service: ServicoService) { }

  ngOnInit() {
  }

  salvar(){
    this.service.salvar(this.servico).subscribe(res=>{
      if(res.status == 200){
        alert("Serviço cadastrado");
      }
    })
  }

}
