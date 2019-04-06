import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CadastrarEstadoComponent } from './person1/view/estado/cadastrar-estado/cadastrar-estado.component';
import { ListarEstadoComponent } from './person1/view/estado/listar-estado/listar-estado.component';

const routes: Routes = [
  { path: 'cadastrarEstado', component: CadastrarEstadoComponent},
  { path: 'listarEstado', component: ListarEstadoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
