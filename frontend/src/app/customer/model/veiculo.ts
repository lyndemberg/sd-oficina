import { Cor } from './cor';
import { Modelo } from './modelo';

export class Veiculo{
    id ?: number;
    placa ?: string;
    quilometragem ?: number;
    cor ?: Cor;
    modelo ?: Modelo;
}