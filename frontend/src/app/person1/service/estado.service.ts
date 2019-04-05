import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estado } from 'src/app/person1/model/estado.model';

@Injectable({
  providedIn: 'root'
})

export class EstadoService {

  constructor(private http: HttpClient) { }

  salvar(Estado): Observable<Estado> {
    return this.http.post<Estado>('//localhost:8080/api/estado', Estado);
  }
}
