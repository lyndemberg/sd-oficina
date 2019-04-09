import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarCorComponent } from './cadastrar-cor.component';

describe('CadastrarCorComponent', () => {
  let component: CadastrarCorComponent;
  let fixture: ComponentFixture<CadastrarCorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastrarCorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastrarCorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
