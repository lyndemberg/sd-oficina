import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarNotaComponent } from './listar-nota.component';

describe('ListarNotaComponent', () => {
  let component: ListarNotaComponent;
  let fixture: ComponentFixture<ListarNotaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListarNotaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarNotaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
