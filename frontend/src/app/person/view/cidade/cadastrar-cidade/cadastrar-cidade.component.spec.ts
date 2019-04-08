import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarCidadeComponent } from './cadastrar-cidade.component';

describe('CadastrarCidadeComponent', () => {
  let component: CadastrarCidadeComponent;
  let fixture: ComponentFixture<CadastrarCidadeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastrarCidadeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastrarCidadeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
