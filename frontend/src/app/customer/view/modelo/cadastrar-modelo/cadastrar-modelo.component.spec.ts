import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarModeloComponent } from './cadastrar-modelo.component';

describe('CadastrarModeloComponent', () => {
  let component: CadastrarModeloComponent;
  let fixture: ComponentFixture<CadastrarModeloComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastrarModeloComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastrarModeloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
