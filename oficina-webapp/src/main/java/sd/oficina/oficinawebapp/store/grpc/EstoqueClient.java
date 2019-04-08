package sd.oficina.oficinawebapp.store.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.proto.customer.EstoqueProto;
import sd.oficina.shared.proto.customer.EstoqueServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class EstoqueClient {

    private ManagedChannel channel;

    public EstoqueClient() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 1111)
                .usePlaintext()
                .build();
    }

    public Estoque salvar(Estoque estoque) {
        return ProtoConverterStore
                .protoToModel(EstoqueServiceGrpc.newBlockingStub(channel)
                        .salvar(ProtoConverterStore.modelToProto(estoque))
                        .getEstoque());
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
