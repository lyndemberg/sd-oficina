import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CadastrarClienteComponent } from './person/view/cliente/cadastrar-cliente/cadastrar-cliente.component';
import { ListarClienteComponent } from './person/view/cliente/listar-cliente/listar-cliente.component';
import { HomeComponent } from './home/home.component';
import { CadastrarFornecedorComponent } from './person/view/fornecedor/cadastrar-fornecedor/cadastrar-fornecedor.component';
import { ListarFornecedorComponent } from './person/view/fornecedor/listar-fornecedor/listar-fornecedor.component';
import { CadastrarAnoModeloComponent } from './customer/view/anoModelo/cadastrar-ano-modelo/cadastrar-ano-modelo.component';
import { ListarAnoModeloComponent} from './customer/view/anoModelo/listar-ano-modelo/listar-ano-modelo.component';
import { CadastrarCorComponent} from  './customer/view/cor/cadastrar-cor/cadastrar-cor.component';
import { ListarCorComponent} from './customer/view/cor/listar-cor/listar-cor.component';
import { CadastrarFabricanteComponent} from  './customer/view/fabricante/cadastrar-fabricante/cadastrar-fabricante.component';
import { ListarFabricanteComponent} from './customer/view/fabricante/listar-fabricante/listar-fabricante.component';
import { CadastrarModeloComponent} from  './customer/view/modelo/cadastrar-modelo/cadastrar-modelo.component';
import { ListarModeloComponent} from './customer/view/modelo/listar-modelo/listar-modelo.component';
import { CadastrarVeiculoComponent} from  './customer/view/veiculo/cadastrar-veiculo/cadastrar-veiculo.component';
import { ListarVeiculoComponent} from './customer/view/veiculo/listar-veiculo/listar-veiculo.component';
import { CadastroOrdemComponent } from './cadastro-ordem/cadastro-ordem.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'cadastrarCliente', component: CadastrarClienteComponent},
  { path: 'listarCliente', component: ListarClienteComponent},
  { path: 'cadastrarFornecedor', component: CadastrarFornecedorComponent},
  { path: 'listarFornecedor', component: ListarFornecedorComponent},
  { path: 'cadastrarAnoModelo', component: CadastrarAnoModeloComponent},
  { path: 'listarAnoModelo', component: ListarAnoModeloComponent},
  { path: 'cadastrarCor', component: CadastrarCorComponent},
  { path: 'listarCor', component: ListarCorComponent},
  { path: 'cadastrarFabricante', component: CadastrarFabricanteComponent},
  { path: 'listarFabricante', component: ListarFabricanteComponent},
  { path: 'cadastrarVeiculo', component: CadastrarVeiculoComponent},
  { path: 'listarVeiculo', component: ListarVeiculoComponent},
  { path: 'cadastrarModelo', component: CadastrarModeloComponent},
  { path: 'listarModelo', component: ListarModeloComponent},
  {path:'cadastroOrdem',component: CadastroOrdemComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
