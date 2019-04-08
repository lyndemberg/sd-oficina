import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarFabricanteComponent } from './cadastrar-fabricante.component';

describe('CadastrarFabricanteComponent', () => {
  let component: CadastrarFabricanteComponent;
  let fixture: ComponentFixture<CadastrarFabricanteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastrarFabricanteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastrarFabricanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
