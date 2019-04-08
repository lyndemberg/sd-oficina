import { Component, OnInit } from '@angular/core';
import { CidadeService } from 'src/app/person1/service/cidade/cidade.service';
import { Cidade } from 'src/app/person1/model/cidade.model';
import { MessageService } from 'primeng/components/common/messageservice';
import { EstadoService } from 'src/app/person1/service/estado/estado.service';
import { Estado } from 'src/app/person1/model/estado.model';

@Component({
  selector: 'app-cadastrar-cidade',
  templateUrl: './cadastrar-cidade.component.html',
  styleUrls: ['./cadastrar-cidade.component.css']
})
export class CadastrarCidadeComponent implements OnInit {

  cidade: Cidade;
  estados: Estado[];

  constructor(private cidadeService: CidadeService,
    private messageService: MessageService,
    private estadoService: EstadoService) { }

  ngOnInit() {
    this.cidade = new Cidade();
    this.listarEstados();
  }

  salvar(cidade) {
      if(cidade.nome == null || cidade.nome == ""){
        this.message('error', 'Erro', 'Por favor preencha todos os campos!');
      } else {
      this.cidadeService.salvar(cidade).subscribe(data => {
        if(data != null) {
          this.message('success', 'Sucesso', 'Cidade salva com sucesso!');
          cidade.nome = null;
          cidade.estado = null;
        } else {
          this.message('error', 'Erro', 'Erro ao salvar cidade!');
        }
      });
    }
  }

  message(severity: string, summary: string, detail: string) {
      this.messageService.add({severity:severity, summary:summary, detail:detail});
  }

  listarEstados() {
    this.estadoService.listar().subscribe(data => {
      this.estados = data;
    });
  }

}
