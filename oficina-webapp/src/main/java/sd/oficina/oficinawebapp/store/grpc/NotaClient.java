package sd.oficina.oficinawebapp.store.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Nota;
import sd.oficina.shared.proto.customer.EstoqueServiceGrpc;
import sd.oficina.shared.proto.customer.NotaProto;
import sd.oficina.shared.proto.customer.NotaServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class NotaClient {

    private ManagedChannel channel;

    public NotaClient() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 1111)
                .usePlaintext()
                .build();
    }

    public Nota salvar(Nota nota) {
        return ProtoConverterStore
                .protoToModel(NotaServiceGrpc.newBlockingStub(channel)
                        .salvar(ProtoConverterStore.modelToProto(nota))
                        .getNota());
    }

    public Nota atualizar(Nota nota) {
        return ProtoConverterStore
                .protoToModel(NotaServiceGrpc.newBlockingStub(channel)
                        .atualizar(ProtoConverterStore.modelToProto(nota))
                        .getNota());
    }

    public Nota buscar(int id) {
        return ProtoConverterStore
                .protoToModel(NotaServiceGrpc.newBlockingStub(channel)
                        .buscar(NotaProto.newBuilder()
                                .setId(id).build())
                        .getNota());
    }

    public List<Nota> todas() {
        List<NotaProto> protos = NotaServiceGrpc
                .newBlockingStub(channel)
                .buscarTodos(Empty.newBuilder().build())
                .getNotaList();

        List<Nota> notas = new ArrayList<>();
        protos.forEach(p -> notas.add(ProtoConverterStore.protoToModel(p)));

        return notas;
    }

    public void deletar(long id) {
        NotaServiceGrpc.newBlockingStub(channel).deletar(NotaProto.newBuilder().setId(id).build());
    }
}
