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
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarEstoque' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarEstoque' }]
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
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarAnoModelo' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarAnoModelo' }]
                    },
                    /*
                    {
                        label: 'Cor',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarCor' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarCor' }]
                    },*/
                    {
                        label: 'Fabricante',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarFabricante' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarFabricante' }]
                    },
                    {
                        label: 'Modelo',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarModelo' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarModelo' }]
                    },
                    {
                        label: 'Veiculo',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Cadastrar', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarVeiculo' },
                            { label: 'Visualizar', icon: 'pi pi-fw pi-list', routerLink: 'listarVeiculo' }]
                    }
                ]
            },
            { separator: true },
            {
                label: 'Order',
                icon: 'pi pi-fw pi-cog',
                items: [
                    {
                        label: 'Ordem de serviço',
                        icon: 'pi pi-fw pi-plus',
                        items: [
                            { label: 'Nova Ordem', icon: 'pi pi-fw pi-save', routerLink: 'cadastroOrdem' },
                            { label: 'Buscar Ordem', icon: 'pi pi-fw pi-search', routerLink: 'listarOrdem' }
                        ]
                    
                    }
                ],
            },
            { separator: true }
        ];
    }
}
