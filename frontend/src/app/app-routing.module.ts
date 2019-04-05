import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CadastrarEstadoComponent } from './person1/view/estado/cadastrar-estado/cadastrar-estado.component';

const routes: Routes = [
  { path: 'cadastrarEstado', component: CadastrarEstadoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
