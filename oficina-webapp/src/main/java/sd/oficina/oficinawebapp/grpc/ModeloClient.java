package sd.oficina.oficinawebapp.grpc;

import sd.oficina.shared.model.customer.Modelo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.proto.ModeloProto;
import sd.oficina.shared.proto.ModeloServiceGrpc;

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
        lista.forEach(f-> modelos.add(ProtoConverter.protoToModel(f)));
        return modelos;
    }

    public Modelo salvar(Modelo modelo) {

        ModeloProto grpc = ProtoConverter.modelToProto(modelo);
        ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channel);
        return ProtoConverter
                .protoToModel(stub
                        .salvar(ProtoConverter.modelToProto(modelo))
                        .getModelo());
    }

    public Modelo buscar(int id) {

        ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channel);
        ModeloProto proto = ModeloProto.newBuilder().setId(id).build();
        return ProtoConverter.protoToModel(stub.buscar(proto).getModelo());
    }

    public Modelo atualizar(Modelo modelo){
        ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channel);
        return ProtoConverter
                .protoToModel(stub
                        .atualizar(ProtoConverter.modelToProto(modelo)).getModelo());
    }

    public void deletar(int id) {
        ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channel);
        stub.deletar(ModeloProto.newBuilder().setId(id).build());
    }
}
