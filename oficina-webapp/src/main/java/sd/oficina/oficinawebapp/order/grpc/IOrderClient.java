package sd.oficina.oficinawebapp.order.grpc;

import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.order.valueobject.OrdemServicoValue;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.proto.order.ClienteProto;
import sd.oficina.shared.proto.order.OrdemProto;

import java.util.List;

public interface IOrderClient {
    void cadastrarNovaOrdem(OrdemServicoValue ordemServicoValue) throws FalhaGrpcException;
    void realizarPagamento(OrdemServicoValue ordemServicoValue) throws FalhaGrpcException;
    OrdemProto concluirOrdem(OrdemServicoValue ordemServicoValue) throws FalhaGrpcException;
    List<OrdemProto> buscarOrdensPorCliente(Cliente cliente) throws FalhaGrpcException;
}
