import { Estado } from './estado.model';
import { Cidade } from './cidade.model';

export interface ICliente {
    id?: number;
    telefoneFixo?: string;
    CEP?: string;
    numero?: number;
    logradouro?: string;
    bairro?: string;
    CPF?: string;
    complemento?: string;
    nome?: string;
    telefoneCelular?: string;
    email?: string;
    estado?: Estado;
    cidade?: Cidade;
}

export class Cliente implements ICliente {
    constructor(
        public id?: number,
        public telefoneFixo?: string,
        public CEP?: string,
        public numero?: number,
        public logradouro?: string,
        public bairro?: string,
        public CPF?: string,
        public complemento?: string,
        public nome?: string,
        public telefoneCelular?: string,
        public email?: string,
        public estado?: Estado,
        public cidade?: Cidade
    ) {}
}