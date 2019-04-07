export interface IEstado {
    id?: number;
    nome?: string;
}

export class Estado implements IEstado {
    constructor(
        public id?: number,
        public nome?: string
    ) {}
}