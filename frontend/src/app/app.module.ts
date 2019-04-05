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

@NgModule({
  declarations: [
    AppComponent,
    CadastrarEstadoComponent,
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
    MessageModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
