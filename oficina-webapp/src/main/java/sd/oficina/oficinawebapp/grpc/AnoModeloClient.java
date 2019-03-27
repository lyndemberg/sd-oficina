package sd.oficina.oficinawebapp.grpc;

import sd.oficina.shared.model.AnoModelo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.proto.AnoModeloProto;
import sd.oficina.shared.proto.AnoModeloServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class AnoModeloClient {

    private ManagedChannel channel;

    public AnoModeloClient() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 2222)
                .usePlaintext()
                .build();
    }

    public AnoModelo salvar(AnoModelo anoModelo) {
        AnoModeloProto grpc = ProtoConverter.modelToProto(anoModelo);
        AnoModeloProto proto =  AnoModeloServiceGrpc
                .newBlockingStub(channel)
                .salvar(ProtoConverter.modelToProto(anoModelo))
                .getAnoModelo();
        return ProtoConverter.protoToModel(proto);
    }

    public AnoModelo buscar(int id) {
        AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channel);
        AnoModeloProto proto = AnoModeloProto.newBuilder().setId(id).build();
        return ProtoConverter.protoToModel(stub.buscar(proto).getAnoModelo());
    }

    public void deletar(int id) {
        AnoModeloServiceGrpc.newBlockingStub(channel).deletar(AnoModeloProto.newBuilder().setId(id).build());
    }

    public AnoModelo atualizar(AnoModelo anoModelo) {

        AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channel);
        return ProtoConverter
                .protoToModel(stub
                        .atualizar(ProtoConverter.modelToProto(anoModelo)).getAnoModelo());
    }

    public List<AnoModelo> todos() {

        List<AnoModeloProto> lista = AnoModeloServiceGrpc
                .newBlockingStub(channel)
                .buscarTodos(Empty.newBuilder().build())
                .getAnoModelosList();

        List<AnoModelo> anoModelos = new ArrayList<>();
        lista.forEach(f-> anoModelos.add(ProtoConverter.protoToModel(f)));
        return anoModelos;
    }
}
