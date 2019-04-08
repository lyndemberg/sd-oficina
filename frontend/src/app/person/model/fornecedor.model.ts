import { Estado } from './estado.model';
import { Cidade } from './cidade.model';

export interface IFornecedor {
    id?: number;
    nomeFantasia?: string;
    razaoSocial?: string;
    vendedor?: string;
    codigo?: number;
    CNPJ?: string;
    logradouro?: string;
    numero?: number;
    telefone?: string;
    complemento?: string;
    bairro?: string;
    CEP?: string;
    estado?: Estado;
    cidade?: Cidade;
}

export class Fornecedor implements IFornecedor {
    constructor(
        public id?: number,
        public nomeFantasia?: string,
        public razaoSocial?: string,
        public vendedor?: string,
        public codigo?: number,
        public CNPJ?: string,
        public logradouro?: string,
        public numero?: number,
        public telefone?: string,
        public complemento?: string,
        public bairro?: string,
        public CEP?: string,
        public estado?: Estado,
        public cidade?: Cidade
    ) {}
}