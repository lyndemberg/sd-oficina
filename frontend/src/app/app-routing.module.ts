import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CadastrarEstadoComponent } from './person1/view/estado/cadastrar-estado/cadastrar-estado.component';
import { ListarEstadoComponent } from './person1/view/estado/listar-estado/listar-estado.component';
import { CadastrarCidadeComponent } from './person1/view/cidade/cadastrar-cidade/cadastrar-cidade.component';
import { ListarCidadeComponent } from './person1/view/cidade/listar-cidade/listar-cidade.component';
import { CadastrarClienteComponent } from './person1/view/cliente/cadastrar-cliente/cadastrar-cliente.component';
import { ListarClienteComponent } from './person1/view/cliente/listar-cliente/listar-cliente.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'cadastrarEstado', component: CadastrarEstadoComponent},
  { path: 'listarEstado', component: ListarEstadoComponent},
  { path: 'cadastrarCidade', component: CadastrarCidadeComponent},
  { path: 'listarCidade', component: ListarCidadeComponent},
  { path: 'cadastrarCliente', component: CadastrarClienteComponent},
  { path: 'listarCliente', component: ListarClienteComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
