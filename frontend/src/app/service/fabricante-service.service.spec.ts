import { TestBed } from '@angular/core/testing';

import { FabricanteServiceService } from './fabricante-service.service';

describe('FabricanteServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FabricanteServiceService = TestBed.get(FabricanteServiceService);
    expect(service).toBeTruthy();
  });
});
