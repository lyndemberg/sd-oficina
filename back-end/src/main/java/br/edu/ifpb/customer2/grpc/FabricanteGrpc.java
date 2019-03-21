package br.edu.ifpb.customer2.grpc;

import br.edu.ifpb.customer2.service.FabricanteService;
import br.edu.ifpb.model.Fabricante;
import br.edu.ifpb.proto.AnoModelo;
import br.edu.ifpb.proto.FabricanteServiceGrpc;
import converter.ProtoConverter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class FabricanteGrpc {

    public Fabricante retorno;

    public FabricanteGrpc(){
        retorno = new Fabricante();
    }

    public Fabricante salvar(Fabricante fabricante) {


        br.edu.ifpb.proto.Fabricante grpc = ProtoConverter.modelToGrpc(fabricante);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost",2222)
                .usePlaintext()
                .build();
        FabricanteServiceGrpc.FabricanteServiceStub stub = FabricanteServiceGrpc.newStub(channel);
        stub.salvar(grpc, new StreamObserver<br.edu.ifpb.proto.Fabricante>() {

            private br.edu.ifpb.proto.Fabricante fabricante;

            @Override
            public void onNext(br.edu.ifpb.proto.Fabricante value) {
                this.fabricante = value;
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Salvou?");
                retorno = ProtoConverter.grpcToModel(this.fabricante);
            }
        });

        return retorno;
    }
}
