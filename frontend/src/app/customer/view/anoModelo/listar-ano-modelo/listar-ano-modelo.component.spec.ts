import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarAnoModeloComponent } from './listar-ano-modelo.component';

describe('ListarAnoModeloComponent', () => {
  let component: ListarAnoModeloComponent;
  let fixture: ComponentFixture<ListarAnoModeloComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListarAnoModeloComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarAnoModeloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
