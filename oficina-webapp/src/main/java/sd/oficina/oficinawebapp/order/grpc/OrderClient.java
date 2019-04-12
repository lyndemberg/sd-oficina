package sd.oficina.oficinawebapp.order.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import sd.oficina.oficinawebapp.config.HostsProperties;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.order.valueobject.OrdemServicoValue;
import sd.oficina.shared.converter.ProtoConverterOrder;
import sd.oficina.shared.model.order.OrdemServico;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.proto.order.ClienteProto;
import sd.oficina.shared.proto.order.OrdemProto;
import sd.oficina.shared.proto.order.OrderServiceGrpc;
import sd.oficina.shared.util.LocalDateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderClient {
    private final HostsProperties hostsProperties;

    public OrderClient(HostsProperties hostsProperties) {
        this.hostsProperties = hostsProperties;
    }

    public void cadastrarNovaOrdem(OrdemServico entity) throws FalhaGrpcException {
        OrdemProto ordemProto = ProtoConverterOrder.modelToProto(entity);
        //tentando com ORDER 1
        try{
            ManagedChannel channelOrder1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getOrder1Host(), hostsProperties.getOrder1Port())
                    .usePlaintext()
                    .build();
            Empty empty = OrderServiceGrpc.newBlockingStub(channelOrder1).cadastrarNovaOrdem(ordemProto);
        } catch (StatusRuntimeException ex1) {
            //tentando com ORDER 2
            try{
                ManagedChannel channelOrder2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getOrder2Host(), hostsProperties.getOrder2Port())
                        .usePlaintext()
                        .build();
            } catch (StatusRuntimeException ex2) {
                throw new FalhaGrpcException();
            }
        }

    }

    public void realizarPagamento(OrdemServico ordemServico) throws FalhaGrpcException {
        OrdemProto ordemProto = OrdemProto.newBuilder()
                .setIdOrdem(ordemServico.getId())
                .build();
        //tentando com ORDER 1
        try{
            ManagedChannel channelOrder1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getOrder1Host(), hostsProperties.getOrder1Port())
                    .usePlaintext()
                    .build();
            OrdemProto atualizado = OrderServiceGrpc.newBlockingStub(channelOrder1)
                    .realizarPagamento(ordemProto);
        } catch (StatusRuntimeException ex1) {
            //tentando com ORDER 2
            try{
                ManagedChannel channelOrder2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getOrder2Host(), hostsProperties.getOrder2Port())
                        .usePlaintext()
                        .build();
                OrdemProto atualizado = OrderServiceGrpc.newBlockingStub(channelOrder2)
                        .realizarPagamento(ordemProto);
            } catch (StatusRuntimeException ex2) {
                throw new FalhaGrpcException();
            }
        }
    }

    public OrdemProto concluirOrdem(OrdemServico ordemServico) throws FalhaGrpcException {
        OrdemProto ordemProto = OrdemProto.newBuilder()
                .setIdOrdem(ordemServico.getId())
                .build();
        //tentando com ORDER 1
        try{
            ManagedChannel channelOrder1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getOrder1Host(), hostsProperties.getOrder1Port())
                    .usePlaintext()
                    .build();
            return OrderServiceGrpc.newBlockingStub(channelOrder1)
                    .concluirOrdem(ordemProto);
        } catch (StatusRuntimeException ex1) {
            //tentando com ORDER 2
            try{
                ManagedChannel channelOrder2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getOrder2Host(), hostsProperties.getOrder2Port())
                        .usePlaintext()
                        .build();
                return OrderServiceGrpc.newBlockingStub(channelOrder2)
                        .concluirOrdem(ordemProto);
            } catch (StatusRuntimeException ex2) {
                throw new FalhaGrpcException();
            }
        }
    }

    public List<OrdemProto> buscarOrdensPorCliente(Cliente cliente) throws FalhaGrpcException {
        List<OrdemProto> ordemProtosList = new ArrayList<>();

        //tentando com ORDER 1
        try{
            ManagedChannel channelOrder1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getOrder1Host(), hostsProperties.getOrder1Port())
                    .usePlaintext()
                    .build();
            Iterator<OrdemProto> iterator = OrderServiceGrpc.newBlockingStub(channelOrder1)
                    .buscarOrdensPorCliente(ClienteProto.newBuilder().setId(cliente.getId()).build());
            while(iterator.hasNext()){
                OrdemProto next = iterator.next();
                ordemProtosList.add(next);
            }
        } catch (StatusRuntimeException ex1) {
            //tentando com ORDER 2
            try{
                ManagedChannel channelOrder2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getOrder2Host(), hostsProperties.getOrder2Port())
                        .usePlaintext()
                        .build();
                Iterator<OrdemProto> iterator = OrderServiceGrpc.newBlockingStub(channelOrder2)
                        .buscarOrdensPorCliente(ClienteProto.newBuilder().setId(cliente.getId()).build());
                while(iterator.hasNext()){
                    OrdemProto next = iterator.next();
                    ordemProtosList.add(next);
                }
            } catch (StatusRuntimeException ex2) {
                throw new FalhaGrpcException();
            }
        }

        return ordemProtosList;
    }
}
