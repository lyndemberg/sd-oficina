package sd.oficina.oficinawebapp.customer.grpc;

import sd.oficina.shared.model.customer.AnoModelo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.proto.customer.AnoModeloProto;
import sd.oficina.shared.proto.customer.AnoModeloServiceGrpc;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class AnoModeloClient {

    private ManagedChannel channel;

    public AnoModeloClient() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 3333)
                .usePlaintext()
                .build();
    }

    public AnoModelo salvar(AnoModelo anoModelo) {
        AnoModeloProto grpc = ProtoConverterCustomer.modelToProto(anoModelo);
        AnoModeloProto proto =  AnoModeloServiceGrpc
                .newBlockingStub(channel)
                .salvar(ProtoConverterCustomer.modelToProto(anoModelo))
                .getAnoModelo();
        return ProtoConverterCustomer.protoToModel(proto);
    }

    public AnoModelo buscar(int id) {
        AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channel);
        AnoModeloProto proto = AnoModeloProto.newBuilder().setId(id).build();
        return ProtoConverterCustomer.protoToModel(stub.buscar(proto).getAnoModelo());
    }

    public void deletar(int id) {

        AnoModeloServiceGrpc.newBlockingStub(channel).deletar(AnoModeloProto.newBuilder().setId(id).build());
    }

    public AnoModelo atualizar(AnoModelo anoModelo) {

        AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channel);
        return ProtoConverterCustomer
                .protoToModel(stub
                        .atualizar(ProtoConverterCustomer.modelToProto(anoModelo)).getAnoModelo());
    }

    public List<AnoModelo> todos() {

        List<AnoModeloProto> lista = AnoModeloServiceGrpc
                .newBlockingStub(channel)
                .buscarTodos(Empty.newBuilder().build())
                .getAnoModelosList();

        List<AnoModelo> anoModelos = new ArrayList<>();
        lista.forEach(f-> anoModelos.add(ProtoConverterCustomer.protoToModel(f)));
        return anoModelos;
    }
}
