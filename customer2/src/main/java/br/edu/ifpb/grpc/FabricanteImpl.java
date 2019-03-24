package br.edu.ifpb.grpc;

import br.edu.ifpb.proto.FabricanteResult;
import converter.ProtoConverter;
import br.edu.ifpb.model.Fabricante;
import br.edu.ifpb.proto.FabricanteServiceGrpc;
import br.edu.ifpb.proto.FabricanteProto;
import br.edu.ifpb.dao.FabricanteDAO;
import io.grpc.stub.StreamObserver;

public class FabricanteImpl extends FabricanteServiceGrpc.FabricanteServiceImplBase {

    private FabricanteDAO dao;

    public FabricanteImpl() {
        this.dao = new FabricanteDAO();
    }

    @Override
    public void salvar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        Fabricante result = this.dao.salvar(ProtoConverter.protoToModel(request));
        FabricanteResult fabricanteResult = FabricanteResult.newBuilder()
                .setCodigo(200)
                .setFabricante(ProtoConverter.modelToProto(result))
                .build();
        responseObserver.onNext(fabricanteResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        Fabricante fabricante = dao.atualizar(ProtoConverter.protoToModel(request));
        responseObserver.onNext(FabricanteResult
                .newBuilder()
                .setFabricante(ProtoConverter.modelToProto(fabricante))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(FabricanteResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        responseObserver.onNext(FabricanteResult
                .newBuilder()
                .setFabricante(
                        ProtoConverter.modelToProto(dao.buscar(request.getId())))
                .build());
        responseObserver.onCompleted();
    }

}
