import { Component, OnInit } from '@angular/core';
import { EstadoService } from 'src/app/person/service/estado/estado.service';
import { Estado } from 'src/app/person/model/estado.model';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-cadastrar-estado',
  templateUrl: './cadastrar-estado.component.html',
  styleUrls: ['./cadastrar-estado.component.css']
})

export class CadastrarEstadoComponent implements OnInit {

  estado: Estado;

  constructor(private estadoService: EstadoService,
    private messageService: MessageService) { }

  ngOnInit() {
    this.estado = new Estado();
  }

  salvar(estado) {
      if(estado.nome == null || estado.nome == ""){
        this.message('error', 'Erro', 'Por favor preencha todos os campos!');
      } else {
      this.estadoService.salvar(estado).subscribe(data => {
        if(data != null) {
          this.message('success', 'Sucesso', 'Estado salvo com sucesso!');
          estado.nome = null;
        } else {
          this.message('error', 'Erro', 'Erro ao salvar estado!');
        }
      });
    }
  }

  message(severity: string, summary: string, detail: string) {
      this.messageService.add({severity:severity, summary:summary, detail:detail});
  }
  
}
