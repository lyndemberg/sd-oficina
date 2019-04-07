import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { CadastrarEstadoComponent } from './person1/view/estado/cadastrar-estado/cadastrar-estado.component';

import {MenubarModule} from 'primeng/menubar';
import {ButtonModule} from 'primeng/button';
import {InputTextModule} from 'primeng/inputtext';
import {CheckboxModule} from 'primeng/checkbox';
import {RadioButtonModule} from 'primeng/radiobutton';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {TableModule} from 'primeng/table';
import { ListarEstadoComponent } from './person1/view/estado/listar-estado/listar-estado.component';
import {DialogModule} from 'primeng/dialog';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import { CadastrarCidadeComponent } from './person1/view/cidade/cadastrar-cidade/cadastrar-cidade.component';
import { ListarCidadeComponent } from './person1/view/cidade/listar-cidade/listar-cidade.component';
import { CadastrarClienteComponent } from './person1/view/cliente/cadastrar-cliente/cadastrar-cliente.component';
import { ListarClienteComponent } from './person1/view/cliente/listar-cliente/listar-cliente.component';
import { CadastrarFornecedorComponent } from './person1/view/fornecedor/cadastrar-fornecedor/cadastrar-fornecedor.component';
import { ListarFornecedorComponent } from './person1/view/fornecedor/listar-fornecedor/listar-fornecedor.component';
import {SplitButtonModule} from 'primeng/splitbutton';
import { MessageService } from 'primeng/components/common/messageservice';
import {ToastModule} from 'primeng/components/toast/toast';
import {DropdownModule} from 'primeng/dropdown';
import {InputMaskModule} from 'primeng/inputmask';
import {KeyFilterModule} from 'primeng/keyfilter';

@NgModule({
  declarations: [
    AppComponent,
    CadastrarEstadoComponent,
    ListarEstadoComponent,
    CadastrarCidadeComponent,
    ListarCidadeComponent,
    CadastrarClienteComponent,
    ListarClienteComponent,
    CadastrarFornecedorComponent,
    ListarFornecedorComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MenubarModule,
    ButtonModule,
    InputTextModule,
    CheckboxModule,
    RadioButtonModule,
    FormsModule,
    HttpClientModule,
    MessagesModule,
    MessageModule,
    TableModule,
    DialogModule,
    SplitButtonModule,
    ToastModule,
    DropdownModule,
    InputMaskModule,
    KeyFilterModule
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
