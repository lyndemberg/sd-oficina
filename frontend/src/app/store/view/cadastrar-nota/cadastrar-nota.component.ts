import { Component, OnInit } from '@angular/core';
import { NotaService } from '../../service/nota.service';
import { Nota } from '../../model/nota';

@Component({
  selector: 'app-cadastrar-nota',
  templateUrl: './cadastrar-nota.component.html',
  styleUrls: ['./cadastrar-nota.component.css']
})
export class CadastrarNotaComponent implements OnInit {

  private nota: Nota = new Nota();

  constructor(public service: NotaService) { }

  ngOnInit() {
  }

  salvar() {
    this.service.salvar(this.nota).subscribe(res => {
      if (res.status == 200) alert("Nota cadastrada");
    })
  }

}
