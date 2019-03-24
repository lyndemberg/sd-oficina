package br.edu.ifpb.customer2.grpc;

import br.edu.ifpb.model.Fabricante;
import br.edu.ifpb.proto.FabricanteProto;
import br.edu.ifpb.proto.FabricanteResult;
import br.edu.ifpb.proto.FabricanteServiceGrpc;
import converter.ProtoConverter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import sun.misc.FDBigInteger;

import java.net.ProtocolException;

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

    public Fabricante salvar(Fabricante fabricante) {

        FabricanteProto grpc = ProtoConverter.modelToProto(fabricante);
        FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channel);
        return ProtoConverter
                .protoToModel(stub
                        .salvar(ProtoConverter.modelToProto(fabricante))
                        .getFabricante());

    }

    public Fabricante buscar(int id) {

        FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channel);
        FabricanteProto proto = FabricanteProto.newBuilder().setId(id).build();
        return ProtoConverter.protoToModel(stub.buscar(proto).getFabricante());
    }

    public Fabricante atualizar(Fabricante fabricante){
        FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channel);
        return ProtoConverter
                .protoToModel(stub
                        .atualizar(ProtoConverter.modelToProto(fabricante)).getFabricante());
    }

    public void deletar(int id) {

        FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channel);
        stub.deletar(FabricanteProto.newBuilder().setId(id).build());
    }
}
