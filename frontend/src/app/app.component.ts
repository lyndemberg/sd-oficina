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
                                {label: 'Save', icon: 'pi pi-fw pi-save', routerLink: 'cadastrarEstado'},
                                {label: 'Update', icon: 'pi pi-fw pi-pencil'},
                                {label: 'Search', icon: 'pi pi-fw pi-search'},
                                {label: 'Delete', icon: 'pi pi-fw pi-trash'},
                                {label: 'List', icon: 'pi pi-fw pi-list', routerLink: 'listarEstado'}                                ]
                            }
                        ]
                    },
                ]
            },
            {separator:true}
        ];
    }
}
