import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarPorClienteComponent } from './listar-por-cliente.component';

describe('ListarPorClienteComponent', () => {
  let component: ListarPorClienteComponent;
  let fixture: ComponentFixture<ListarPorClienteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListarPorClienteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarPorClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
