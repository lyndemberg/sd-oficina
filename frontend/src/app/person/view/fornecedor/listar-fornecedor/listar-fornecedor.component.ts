import { Component, OnInit } from '@angular/core';
import { Fornecedor } from 'src/app/person1/model/fornecedor.model';
import { Estado } from 'src/app/person1/model/estado.model';
import { Cidade } from 'src/app/person1/model/cidade.model';
import { FornecedorService } from 'src/app/person1/service/fornecedor/fornecedor.service';
import { EstadoService } from 'src/app/person1/service/estado/estado.service';
import { CidadeService } from 'src/app/person1/service/cidade/cidade.service';

@Component({
  selector: 'app-listar-fornecedor',
  templateUrl: './listar-fornecedor.component.html',
  styleUrls: ['./listar-fornecedor.component.css']
})
export class ListarFornecedorComponent implements OnInit {

  fornecedores: Fornecedor[];

  fornecedor: Fornecedor;

  estados: Estado[];

  cidades: Cidade[];

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(private fornecedorService: FornecedorService,
    private estadoService: EstadoService,
    private cidadeService: CidadeService) { }

  ngOnInit() {
    this.fornecedor = new Fornecedor();
    this.listar();
    this.listarEstados();
    this.listarCidades();
  }

  listar() {
    this.fornecedorService.listar().subscribe(data => {
      this.fornecedores = data;
    });
  }

  atualizar(fornecedor) {
    this.fornecedorService.atualizar(fornecedor).subscribe(data => {
    });
    this.displayUpdate = false;
  }

  deletar(fornecedor) {
    this.fornecedorService.deletar(fornecedor.id).subscribe(data => {
    });
    this.displayDelete = false;
    location.reload();
  }

  showDialogUpdate(fornecedor) {
    this.fornecedor = fornecedor;
    this.displayUpdate = true;
  }

  showDialogDelete(fornecedor) {
    this.fornecedor = fornecedor;
    this.displayDelete = true;
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
