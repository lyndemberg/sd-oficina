package sd.oficina.oficinawebapp.order.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.order.valueobject.OrdemServicoValue;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.proto.order.ClienteProto;
import sd.oficina.shared.proto.order.OrdemProto;
import sd.oficina.shared.proto.order.OrderServiceGrpc;
import sd.oficina.shared.util.LocalDateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderClientImpl implements IOrderClient {

    private ManagedChannel channel;

    public OrderClientImpl() {
        this.channel = ManagedChannelBuilder
                .forAddress("localhost", 4444)
                .usePlaintext()
                .build();
    }


    @Override
    public void cadastrarNovaOrdem(OrdemServicoValue value) throws FalhaGrpcException {
        OrdemProto ordemProto = OrdemProto.newBuilder()
                .setIdVeiculo(value.getVeiculo().getId())
                .setIdCliente(value.getCliente().getId())
                .setPago(value.isPago())
                .setConcluido(value.isConcluida())
                .setDataRegistro(LocalDateUtil.toString(value.getDataRegistro()))
                .setDataPagamento(LocalDateUtil.toString(value.getDataPagamento()))
                .addAllServicos(value.getIdServicos())
                .build();

        try{
            Empty empty = OrderServiceGrpc.newBlockingStub(channel)
                    .cadastrarNovaOrdem(ordemProto);
        } catch (StatusRuntimeException e) {
            throw new FalhaGrpcException();
        }
    }

    @Override
    public void realizarPagamento(OrdemServicoValue ordemServicoValue) throws FalhaGrpcException {
        OrdemProto ordemProto = OrdemProto.newBuilder()
                .setIdOrdem(ordemServicoValue.getId())
                .build();
        try{
            OrdemProto atualizado = OrderServiceGrpc.newBlockingStub(channel)
                    .realizarPagamento(ordemProto);
        }catch(StatusRuntimeException e){
            throw new FalhaGrpcException();
        }
    }

    @Override
    public OrdemProto concluirOrdem(OrdemServicoValue ordemServicoValue) throws FalhaGrpcException {
        OrdemProto ordemProto = OrdemProto.newBuilder()
                .setIdOrdem(ordemServicoValue.getId())
                .build();
        try{
            return OrderServiceGrpc.newBlockingStub(channel)
                    .realizarPagamento(ordemProto);
        }catch(StatusRuntimeException e){
            throw new FalhaGrpcException();
        }
    }

    @Override
    public List<OrdemProto> buscarOrdensPorCliente(Cliente cliente) throws FalhaGrpcException {
        List<OrdemProto> ordemProtosList = new ArrayList<>();
        try{
            Iterator<OrdemProto> iterator = OrderServiceGrpc.newBlockingStub(channel)
                    .buscarOrdensPorCliente(ClienteProto.newBuilder().setId(cliente.getId()).build());
            while(iterator.hasNext()){
                OrdemProto next = iterator.next();
                ordemProtosList.add(next);
            }
        }catch(StatusRuntimeException e){
            throw new FalhaGrpcException();
        }

        return ordemProtosList;
    }
}
