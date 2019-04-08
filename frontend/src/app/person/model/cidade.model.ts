import { Estado } from './estado.model';

export interface ICidade {
    id?: number;
    nome?: string;
    estado?: Estado;
}

export class Cidade implements ICidade {
    constructor(
        public id?: number,
        public nome?: string,
        public estado?: Estado
    ) {}
}