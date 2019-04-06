import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CadastrarEstadoComponent } from './person1/view/estado/cadastrar-estado/cadastrar-estado.component';
import { ListarEstadoComponent } from './person1/view/estado/listar-estado/listar-estado.component';
import { CadastrarCidadeComponent } from './person1/view/cidade/cadastrar-cidade/cadastrar-cidade.component';
import { ListarCidadeComponent } from './person1/view/cidade/listar-cidade/listar-cidade.component';

const routes: Routes = [
  { path: 'cadastrarEstado', component: CadastrarEstadoComponent},
  { path: 'listarEstado', component: ListarEstadoComponent},
  { path: 'cadastrarCidade', component: CadastrarCidadeComponent},
  { path: 'listarCidade', component: ListarCidadeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
