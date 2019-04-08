package sd.oficina.oficinawebapp.customer.grpc;

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
                .forAddress("localhost", 3333)
                .usePlaintext()
                .build();
    }

    public List<Estoque> todos() {
        EstoqueServiceGrpc.EstoqueServiceBlockingStub stub = EstoqueServiceGrpc.newBlockingStub(channel);
        List<EstoqueProto> lista = stub.buscarTodos(Empty.newBuilder().build()).getEstoqueList();
        List<Estoque> Estoques = new ArrayList<>();
        lista.forEach(f -> Estoques.add(ProtoConverterStore.protoToModel(f)));
        return Estoques;
    }

    public Estoque salvar(Estoque Estoque) {

        EstoqueProto grpc = ProtoConverterStore.modelToProto(Estoque);
        EstoqueServiceGrpc.EstoqueServiceBlockingStub stub = EstoqueServiceGrpc.newBlockingStub(channel);
        return ProtoConverterStore
                .protoToModel(stub
                        .salvar(ProtoConverterStore.modelToProto(Estoque))
                        .getEstoque());
    }

    public Estoque buscar(int idPeca) {

        EstoqueServiceGrpc.EstoqueServiceBlockingStub stub = EstoqueServiceGrpc.newBlockingStub(channel);
        EstoqueProto proto = EstoqueProto.newBuilder().setIdPeca(idPeca).build();
        return ProtoConverterStore.protoToModel(stub.buscar(proto).getEstoque());
    }

    public Estoque atualizar(Estoque Estoque) {
        EstoqueServiceGrpc.EstoqueServiceBlockingStub stub = EstoqueServiceGrpc.newBlockingStub(channel);
        return ProtoConverterStore
                .protoToModel(stub
                        .atualizar(ProtoConverterStore.modelToProto(Estoque)).getEstoque());
    }

    public void deletar(int idPeca) {

        EstoqueServiceGrpc.EstoqueServiceBlockingStub stub = EstoqueServiceGrpc.newBlockingStub(channel);
        stub.deletar(EstoqueProto.newBuilder().setIdPeca(idPeca).build());
    }
}
