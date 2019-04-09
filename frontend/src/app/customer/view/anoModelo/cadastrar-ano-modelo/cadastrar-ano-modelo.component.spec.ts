import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarAnoModeloComponent } from './cadastrar-ano-modelo.component';

describe('CadastrarAnoModeloComponent', () => {
  let component: CadastrarAnoModeloComponent;
  let fixture: ComponentFixture<CadastrarAnoModeloComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastrarAnoModeloComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastrarAnoModeloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
