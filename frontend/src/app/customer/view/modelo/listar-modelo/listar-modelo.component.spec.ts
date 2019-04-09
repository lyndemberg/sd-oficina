import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarModeloComponent } from './listar-modelo.component';

describe('ListarModeloComponent', () => {
  let component: ListarModeloComponent;
  let fixture: ComponentFixture<ListarModeloComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListarModeloComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarModeloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
