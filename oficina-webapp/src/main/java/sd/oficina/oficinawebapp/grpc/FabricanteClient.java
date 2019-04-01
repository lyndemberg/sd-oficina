package sd.oficina.oficinawebapp.grpc;

import sd.oficina.shared.model.customer.Fabricante;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.proto.customer.FabricanteProto;
import sd.oficina.shared.proto.customer.FabricanteServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class FabricanteClient {

    private Fabricante retorno;
    private ManagedChannel channel;

    public FabricanteClient() {
        retorno = new Fabricante();
        channel = ManagedChannelBuilder
                .forAddress("localhost", 2222)
                .usePlaintext()
                .build();
    }

    public List<Fabricante> todos(){
        FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channel);
        List<FabricanteProto> lista = stub.buscarTodos(Empty.newBuilder().build()).getFabricantesList();
        List<Fabricante> fabricantes = new ArrayList<>();
        lista.forEach(f-> fabricantes.add(ProtoConverterCustomer.protoToModel(f)));
        return fabricantes;
    }

    public Fabricante salvar(Fabricante fabricante) {

        FabricanteProto grpc = ProtoConverterCustomer.modelToProto(fabricante);
        FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channel);
        return ProtoConverterCustomer
                .protoToModel(stub
                        .salvar(ProtoConverterCustomer.modelToProto(fabricante))
                        .getFabricante());
    }

    public Fabricante buscar(int id) {

        FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channel);
        FabricanteProto proto = FabricanteProto.newBuilder().setId(id).build();
        return ProtoConverterCustomer.protoToModel(stub.buscar(proto).getFabricante());
    }

    public Fabricante atualizar(Fabricante fabricante){
        FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channel);
        return ProtoConverterCustomer
                .protoToModel(stub
                        .atualizar(ProtoConverterCustomer.modelToProto(fabricante)).getFabricante());
    }

    public void deletar(int id) {

        FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channel);
        stub.deletar(FabricanteProto.newBuilder().setId(id).build());
    }
}
