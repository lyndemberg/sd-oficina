import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/components/common/menuitem';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  items: MenuItem[];

    ngOnInit() {
        this.items = [
            {label: 'Home', icon: 'pi pi-home', routerLink: 'home'},
            {
                label: 'Gerenciar',
                icon: 'pi pi-fw pi-cog',
                items: [
                    {
                        label: 'Person1',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            {
                                label: 'Estado',
                                icon: 'pi pi-fw pi-plus',
                                items: [
                                {label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarEstado'},
                                {label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarEstado'}                                ]
                            },
                            {
                                label: 'Cidade',
                                icon: 'pi pi-fw pi-plus',
                                items: [
                                {label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarCidade'},
                                {label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarCidade'}                                ]
                            },
                            {
                                label: 'Cliente',
                                icon: 'pi pi-fw pi-plus',
                                items: [
                                {label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarCliente'},
                                {label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarCliente'}                                ]
                            },
                            {
                                label: 'Fornecedor',
                                icon: 'pi pi-fw pi-plus',
                                items: [
                                {label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarEstado'},
                                {label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarEstado'}                                ]
                            }
                        ]
                    },
                ]
            },
            {separator:true}
        ];
    }
}
