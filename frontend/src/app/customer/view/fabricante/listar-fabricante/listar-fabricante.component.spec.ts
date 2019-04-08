import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarFabricanteComponent } from './listar-fabricante.component';

describe('ListarFabricanteComponent', () => {
  let component: ListarFabricanteComponent;
  let fixture: ComponentFixture<ListarFabricanteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListarFabricanteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarFabricanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
