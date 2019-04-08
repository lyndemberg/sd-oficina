import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CadastrarEstadoComponent } from './person/view/estado/cadastrar-estado/cadastrar-estado.component';
import { ListarEstadoComponent } from './person/view/estado/listar-estado/listar-estado.component';
import { CadastrarCidadeComponent } from './person/view/cidade/cadastrar-cidade/cadastrar-cidade.component';
import { ListarCidadeComponent } from './person/view/cidade/listar-cidade/listar-cidade.component';
import { CadastrarClienteComponent } from './person/view/cliente/cadastrar-cliente/cadastrar-cliente.component';
import { ListarClienteComponent } from './person/view/cliente/listar-cliente/listar-cliente.component';
import { HomeComponent } from './home/home.component';
import { CadastrarFornecedorComponent } from './person/view/fornecedor/cadastrar-fornecedor/cadastrar-fornecedor.component';
import { ListarFornecedorComponent } from './person/view/fornecedor/listar-fornecedor/listar-fornecedor.component';
import { CadastrarAnoModeloComponent } from './customer/view/anoModelo/cadastrar-ano-modelo/cadastrar-ano-modelo.component';
import { ListarAnoModeloComponent} from './customer/view/anoModelo/listar-ano-modelo/listar-ano-modelo.component';
import { CadastrarCorComponent} from  './customer/view/cor/cadastrar-cor/cadastrar-cor.component';
import { ListarCorComponent} from './customer/view/cor/listar-cor/listar-cor.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'cadastrarEstado', component: CadastrarEstadoComponent},
  { path: 'listarEstado', component: ListarEstadoComponent},
  { path: 'cadastrarCidade', component: CadastrarCidadeComponent},
  { path: 'listarCidade', component: ListarCidadeComponent},
  { path: 'cadastrarCliente', component: CadastrarClienteComponent},
  { path: 'listarCliente', component: ListarClienteComponent},
  { path: 'cadastrarFornecedor', component: CadastrarFornecedorComponent},
  { path: 'listarFornecedor', component: ListarFornecedorComponent},
  { path: 'cadastrarAnoModelo', component: CadastrarAnoModeloComponent},
  { path: 'listarAnoModelo', component: ListarAnoModeloComponent},
  { path: 'cadastrarCor', component: CadastrarCorComponent},
  { path: 'listarCor', component: ListarCorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
