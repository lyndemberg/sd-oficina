package sd.oficina.oficinawebapp.store.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import sd.oficina.oficinawebapp.config.HostsProperties;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.proto.customer.EstoqueProto;
import sd.oficina.shared.proto.customer.EstoqueServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class EstoqueClient {

    private final HostsProperties properties;
    private ManagedChannel channel;

    public EstoqueClient(HostsProperties hostsProperties) {
        this.properties = hostsProperties;
        channel = ManagedChannelBuilder
                .forAddress(properties.getStore1Host(), properties.getStore1Port())
                .usePlaintext()
                .build();
    }

    public Estoque salvar(Estoque estoque) throws FalhaGrpcException {

        Estoque salvo = null;
        try {
            salvo = ProtoConverterStore
                    .protoToModel(EstoqueServiceGrpc.newBlockingStub(channel)
                            .salvar(ProtoConverterStore.modelToProto(estoque))
                            .getEstoque());
        } catch (StatusRuntimeException e) {
            // STORE 2
            try {
                channel = ManagedChannelBuilder
                        .forAddress(properties.getStore2Host(), properties.getStore2Port())
                        .usePlaintext()
                        .build();
                salvo = ProtoConverterStore
                        .protoToModel(EstoqueServiceGrpc.newBlockingStub(channel)
                                .salvar(ProtoConverterStore.modelToProto(estoque))
                                .getEstoque());

            } catch (StatusRuntimeException ex) {
                throw new FalhaGrpcException();
            }
        }
        return salvo;
    }

    public Estoque atualizar(Estoque estoque) {
        return ProtoConverterStore
                .protoToModel(EstoqueServiceGrpc.newBlockingStub(channel)
                        .atualizar(ProtoConverterStore.modelToProto(estoque))
                        .getEstoque());
    }

    public void deletar(int id) {
        EstoqueServiceGrpc.newBlockingStub(channel).deletar(EstoqueProto.newBuilder().setIdPeca(id).build());
    }

    public Estoque buscar(int id) {
        return ProtoConverterStore
                .protoToModel(EstoqueServiceGrpc.newBlockingStub(channel)
                        .buscar(EstoqueProto.newBuilder()
                                .setIdPeca(id).build())
                        .getEstoque());
    }

    public List<Estoque> buscarTodos() {

        List<EstoqueProto> protos = EstoqueServiceGrpc
                .newBlockingStub(channel)
                .buscarTodos(Empty.newBuilder().build())
                .getEstoqueList();

        List<Estoque> estoques = new ArrayList<>();
        protos.forEach(p -> estoques.add(ProtoConverterStore.protoToModel(p)));

        return estoques;
    }
}
