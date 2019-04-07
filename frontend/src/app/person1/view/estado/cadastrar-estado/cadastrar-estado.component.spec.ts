import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarEstadoComponent } from './cadastrar-estado.component';

describe('CadastrarEstadoComponent', () => {
  let component: CadastrarEstadoComponent;
  let fixture: ComponentFixture<CadastrarEstadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastrarEstadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastrarEstadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
