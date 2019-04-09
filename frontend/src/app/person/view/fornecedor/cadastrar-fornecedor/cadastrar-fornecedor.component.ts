import { Component, OnInit } from '@angular/core';
import { Fornecedor } from 'src/app/person/model/fornecedor.model';
import { Estado } from 'src/app/person/model/estado.model';
import { Cidade } from 'src/app/person/model/cidade.model';
import { FornecedorService } from 'src/app/person/service/fornecedor/fornecedor.service';
import { MessageService } from 'primeng/components/common/messageservice';
import { EstadoService } from 'src/app/person/service/estado/estado.service';
import { CidadeService } from 'src/app/person/service/cidade/cidade.service';

@Component({
  selector: 'app-cadastrar-fornecedor',
  templateUrl: './cadastrar-fornecedor.component.html',
  styleUrls: ['./cadastrar-fornecedor.component.css']
})
export class CadastrarFornecedorComponent implements OnInit {

  fornecedor: Fornecedor;
  estados: Estado[];
  cidades: Cidade[];

  constructor(private fornecedorService: FornecedorService,
    private messageService: MessageService,
    private estadoService: EstadoService,
    private cidadeService: CidadeService) { }

  ngOnInit() {
    this.fornecedor = new Fornecedor();
    this.listarEstados();
  }

  salvar(fornecedor) {
    if(fornecedor.nomeFantasia == null || fornecedor.nomeFantasia == ""){
      this.message('error', 'Erro', 'Por favor preencha todos os campos!');
    } else {
    this.fornecedorService.salvar(fornecedor).subscribe(data => {
      if(data != null) {
        this.message('success', 'Sucesso', 'Fornecedor salvo com sucesso!');
        fornecedor.nomeFantasia = null;
        fornecedor.razaoSocial = null;
        fornecedor.vendedor = null;
        fornecedor.codigo = null;
        fornecedor.cnpj = null;
        fornecedor.logradouro = null;
        fornecedor.numero = null;
        fornecedor.telefone = null;
        fornecedor.complemento = null;
        fornecedor.bairro = null;
        fornecedor.cep = null;
        fornecedor.estado = null;
        fornecedor.cidade = null;
      } else {
        this.message('error', 'Erro', 'Erro ao salvar fornecedor!');
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

listarCidadesPorEstado() {
  this.cidadeService.listarPorEstado(this.fornecedor.estado.id).subscribe(data => {
    this.cidades = data;
  });
}

}
