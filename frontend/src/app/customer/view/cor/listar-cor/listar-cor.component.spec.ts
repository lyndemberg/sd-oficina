import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarCorComponent } from './listar-cor.component';

describe('ListarCorComponent', () => {
  let component: ListarCorComponent;
  let fixture: ComponentFixture<ListarCorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListarCorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarCorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
