import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/person/model/cliente.model';
import { ClienteService } from 'src/app/person/service/cliente/cliente.service';
import { MessageService } from 'primeng/components/common/messageservice';
import { EstadoService } from 'src/app/person/service/estado/estado.service';
import { Estado } from 'src/app/person/model/estado.model';
import { Cidade } from 'src/app/person/model/cidade.model';
import { CidadeService } from 'src/app/person/service/cidade/cidade.service';

@Component({
  selector: 'app-cadastrar-cliente',
  templateUrl: './cadastrar-cliente.component.html',
  styleUrls: ['./cadastrar-cliente.component.css']
})
export class CadastrarClienteComponent implements OnInit {

  cliente: Cliente;
  estados: Estado[];
  cidades: Cidade[];

  constructor(private clienteService: ClienteService,
    private messageService: MessageService,
    private estadoService: EstadoService,
    private cidadeService: CidadeService) { }

  ngOnInit() {
    this.cliente = new Cliente();
    this.listarEstados();
    this.listarCidades();
  }

  salvar(cliente) {
    if(cliente.nome == null || cliente.nome == ""){
      this.message('error', 'Erro', 'Por favor preencha todos os campos!');
    } else {
    this.clienteService.salvar(cliente).subscribe(data => {
      if(data != null) {
        this.message('success', 'Sucesso', 'Cliente salvo com sucesso!');
        cliente.telefoneFixo = null;
        cliente.cep = null;
        cliente.numero = null;
        cliente.logradouro = null;
        cliente.bairro = null;
        cliente.cpf = null;
        cliente.complemento = null;
        cliente.nome = null;
        cliente.telefoneCelular = null;
        cliente.email = null;
        cliente.estado = null;
        cliente.cidade = null;
      } else {
        this.message('error', 'Erro', 'Erro ao salvar cliente!');
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

listarCidades() {
  this.cidadeService.listar().subscribe(data => {
    this.cidades = data;
  });
}

}
