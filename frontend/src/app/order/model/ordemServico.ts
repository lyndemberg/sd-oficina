import { Cliente } from 'src/app/person/model/cliente.model';
import { Veiculo } from 'src/app/customer/model/veiculo';
import { Servico } from 'src/app/store/model/servico';

export class OrdemServico{
    cliente ?: Cliente;
    veiculo ?: Veiculo;
    dataRegistro ?: Date;
    dataPagamento ?: Date;
    pago ?: boolean;
    concluida ?: boolean;
    servicos ?: Servico[];
}