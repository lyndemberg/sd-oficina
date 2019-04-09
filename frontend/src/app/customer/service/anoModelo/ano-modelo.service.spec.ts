import { TestBed } from '@angular/core/testing';

import { AnoModeloService } from './ano-modelo.service';

describe('AnoModeloService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AnoModeloService = TestBed.get(AnoModeloService);
    expect(service).toBeTruthy();
  });
});
