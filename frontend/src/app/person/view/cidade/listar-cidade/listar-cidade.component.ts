import { Component, OnInit } from '@angular/core';
import { Cidade } from 'src/app/person/model/cidade.model';
import { CidadeService } from 'src/app/person/service/cidade/cidade.service';
import { Estado } from 'src/app/person/model/estado.model';
import { EstadoService } from 'src/app/person/service/estado/estado.service';

@Component({
  selector: 'app-listar-cidade',
  templateUrl: './listar-cidade.component.html',
  styleUrls: ['./listar-cidade.component.css']
})
export class ListarCidadeComponent implements OnInit {

  cidades: Cidade[];

  cidade: Cidade;

  estados: Estado[];

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(private cidadeService: CidadeService,
    private estadoService: EstadoService) { }

  ngOnInit() {
    this.cidade = new Cidade();
    this.listar();
    this.listarEstados();
  }

  listar() {
    this.cidadeService.listar().subscribe(data => {
      this.cidades = data;
    });
  }

  atualizar(cidade) {
    this.cidadeService.atualizar(cidade).subscribe(data => {
    });
    this.displayUpdate = false;
  }

  deletar(cidade) {
    this.cidadeService.deletar(cidade.id).subscribe(data => {
    });
    this.displayDelete = false;
    location.reload();
  }

  showDialogUpdate(cidade) {
    this.cidade = cidade;
    this.displayUpdate = true;
  }

  showDialogDelete(cidade) {
    this.cidade = cidade;
    this.displayDelete = true;
  }

  listarEstados() {
    this.estadoService.listar().subscribe(data => {
      this.estados = data;
    });
  }

}
