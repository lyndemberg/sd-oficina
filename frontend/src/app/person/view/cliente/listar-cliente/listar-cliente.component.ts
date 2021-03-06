import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/person/model/cliente.model';
import { ClienteService } from 'src/app/person/service/cliente/cliente.service';
import { Estado } from 'src/app/person/model/estado.model';
import { EstadoService } from 'src/app/person/service/estado/estado.service';
import { CidadeService } from 'src/app/person/service/cidade/cidade.service';
import { Cidade } from 'src/app/person/model/cidade.model';

@Component({
  selector: 'app-listar-cliente',
  templateUrl: './listar-cliente.component.html',
  styleUrls: ['./listar-cliente.component.css']
})
export class ListarClienteComponent implements OnInit {

  clientes: Cliente[];

  cliente: Cliente;

  estados: Estado[];

  cidades: Cidade[];

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(private clienteService: ClienteService,
    private estadoService: EstadoService,
    private cidadeService: CidadeService) { }

  ngOnInit() {
    this.cliente = new Cliente();
    this.listar();
    this.listarEstados();
  }

  listar() {
    this.clienteService.listar().subscribe(data => {
      this.clientes = data.body;
    });
  }

  atualizar(cliente) {
    this.clienteService.atualizar(cliente).subscribe(data => {
    });
    this.displayUpdate = false;
  }

  deletar(cliente) {
    this.clienteService.deletar(cliente.id).subscribe(data => {
    });
    this.displayDelete = false;
    location.reload();
  }

  showDialogUpdate(cliente) {
    this.cliente = cliente;

    this.listarCidadesPorEstado();

    this.displayUpdate = true;
  }

  showDialogDelete(cliente) {
    this.cliente = cliente;
    this.displayDelete = true;
  }

  listarEstados() {
    this.estadoService.listar().subscribe(data => {
      this.estados = data;
    });
  }

  listarCidadesPorEstado() {
    this.cidadeService.listarPorEstado(this.cliente.estado.id).subscribe(data => {
      this.cidades = data;
    });
  }

}
