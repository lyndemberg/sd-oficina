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
            { label: 'Home', icon: 'pi pi-home', routerLink: 'home' },
            { separator: true },
            {
                label: 'Person',
                icon: 'pi pi-fw pi-cog',
                items: [
                    {
                        label: 'Estado',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarEstado' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarEstado' }]
                    },
                    {
                        label: 'Cidade',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarCidade' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarCidade' }]
                    },
                    {
                        label: 'Cliente',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarCliente' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarCliente' }]
                    },
                    {
                        label: 'Fornecedor',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarFornecedor' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarFornecedor' }]
                    }
                ]
            },
            { separator: true },
            {
                label: 'Store',
                icon: 'pi pi-fw pi-cog',
                items: [
                    {
                        label: 'Estoque',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarEstado' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarEstado' }]
                    },
                    {
                        label: 'Serviço',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarCidade' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarCidade' }]
                    }
                ]
            },
            { separator: true },
            {
                label: 'Customer',
                icon: 'pi pi-fw pi-cog',
                items: [
                    {
                        label: 'Ano Modelo',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarEstado' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarEstado' }]
                    },
                    {
                        label: 'Fabricante',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarCidade' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarCidade' }]
                    },
                    {
                        label: 'Modelo',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarCliente' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarCliente' }]
                    },
                    {
                        label: 'Veiculo',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarFornecedor' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarFornecedor' }]
                    }
                ]
            },
            { separator: true },
            {
                label: 'Order',
                icon: 'pi pi-fw pi-cog',
                items: [
                    {
                        label: 'Orçamento',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarEstado' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarEstado' }]
                    },
                    {
                        label: 'Ordem de serviço',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarCidade' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarCidade' }]
                    }
                ]
            },
            { separator: true }
        ];
    }
}
