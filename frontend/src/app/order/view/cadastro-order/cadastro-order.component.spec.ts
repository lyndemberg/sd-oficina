import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroOrderComponent } from './cadastro-order.component';

describe('CadastroOrderComponent', () => {
  let component: CadastroOrderComponent;
  let fixture: ComponentFixture<CadastroOrderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastroOrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastroOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
