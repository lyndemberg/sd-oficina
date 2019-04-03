package sd.oficina.oficinawebapp.customer.grpc;

import sd.oficina.shared.model.customer.Modelo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.proto.customer.ModeloProto;
import sd.oficina.shared.proto.customer.ModeloServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class ModeloClient {

    private ManagedChannel channel;

    public ModeloClient() {

        channel = ManagedChannelBuilder
                .forAddress("localhost", 2222)
                .usePlaintext()
                .build();
    }

    public List<Modelo> todos(){
        ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channel);
        List<ModeloProto> lista = stub.buscarTodos(Empty.newBuilder().build()).getModelosList();
        List<Modelo> modelos = new ArrayList<>();
        lista.forEach(f-> modelos.add(ProtoConverterCustomer.protoToModel(f)));
        return modelos;
    }

    public Modelo salvar(Modelo modelo) {

        ModeloProto grpc = ProtoConverterCustomer.modelToProto(modelo);
        ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channel);
        return ProtoConverterCustomer
                .protoToModel(stub
                        .salvar(ProtoConverterCustomer.modelToProto(modelo))
                        .getModelo());
    }

    public Modelo buscar(int id) {

        ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channel);
        ModeloProto proto = ModeloProto.newBuilder().setId(id).build();
        return ProtoConverterCustomer.protoToModel(stub.buscar(proto).getModelo());
    }

    public Modelo atualizar(Modelo modelo){
        ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channel);
        return ProtoConverterCustomer
                .protoToModel(stub
                        .atualizar(ProtoConverterCustomer.modelToProto(modelo)).getModelo());
    }

    public void deletar(int id) {
        ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channel);
        stub.deletar(ModeloProto.newBuilder().setId(id).build());
    }
}
