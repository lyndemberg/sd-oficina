import { Component, OnInit } from '@angular/core';
import { Servico } from 'src/app/store/model/servico';
import { ServicoService } from 'src/app/store/service/servico.service';
import { LineToLineMappedSource } from 'webpack-sources';

@Component({
  selector: 'app-listar-servico',
  templateUrl: './listar-servico.component.html',
  styleUrls: ['./listar-servico.component.css']
})
export class ListarServicoComponent implements OnInit {

   servicos: any[];

  servico: Servico;

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(public service: ServicoService) { }

  ngOnInit() {
    this.todos();
    this.servico = new Servico();
  }

  showDialogUpdate(servico) {
    this.servico = servico;
    this.displayUpdate = true;
  }

  showDialogDelete(servico) {
    this.servico = servico;
    this.displayDelete = true;
  }

  atualizar(servico: Servico){
    this.service.atualizar(servico).subscribe(res=>{
      
        alert("Serviço atualizado");
      
    });
  }

  deletar(servico: Servico){
    this.service.deletar(servico.id).subscribe(res=>{
        alert("Serviço atualizado");
    });
  }

  todos() {
    this.service.buscarTodos().subscribe(res => {
      console.log(res.body);
      this.servicos = res.body;
    });
  }
}
