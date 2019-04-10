import { Component, OnInit } from '@angular/core';
import { AnoModelo } from 'src/app/customer/model/anoModelo';
import { AnoModeloService } from 'src/app/customer/service/anoModelo/ano-modelo.service';

@Component({
  selector: 'app-listar-ano-modelo',
  templateUrl: './listar-ano-modelo.component.html',
  styleUrls: ['./listar-ano-modelo.component.css']
})
export class ListarAnoModeloComponent implements OnInit {

  anoModelos: any [];
  anoModelo: AnoModelo = {
    modelo: null,
    nome: '',
    tipo: '',
    valor: 0
  }

  displayUpdate: boolean = false;
  displayDelete: boolean = false;

  constructor(private anoModeloServive :  AnoModeloService) { }

  ngOnInit() {
    this.getAll();
  }

  showDialogUpdate(anoModelo) {
    this.anoModelo = anoModelo;
    this.displayUpdate = true;
  }

  showDialogDelete(anoModelo) {
    this.anoModelo = anoModelo;
    this.displayDelete = true;
  }

  atualizar(anoModelo: AnoModelo) {
    this.anoModeloServive.atualizar(anoModelo).subscribe(res => {
      alert("Ano Modelo atualizado");
    })
  }

  deletar(anoModelo: AnoModelo) {
    this.anoModeloServive.deletar(anoModelo.id).subscribe(res => {
      if (res.status == 200) {
        alert("Ano Modelo deletado");
      }
    });
  }

  getAll() {
    this.anoModeloServive.listarTodos().subscribe(res => {
      console.log(res.body.toString());
      this.anoModelos = res.body;
    });
  }
}
