package br.edu.ifpb.grpc;

import br.edu.ifpb.dao.AnoModeloDAO;
import br.edu.ifpb.model.AnoModelo;
import br.edu.ifpb.model.Fabricante;
import br.edu.ifpb.proto.*;
import com.google.protobuf.Empty;
import converter.ProtoConverter;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class AnoModeloImpl extends AnoModeloServiceGrpc.AnoModeloServiceImplBase {

    private AnoModeloDAO dao;

    public AnoModeloImpl() { dao = new AnoModeloDAO();}


    @Override
    public void buscarTodos(Empty request, StreamObserver<AnoModeloProtoList> responseObserver) {

        List<AnoModelo> anoModelos = dao.todos();
        final AnoModeloProtoList.Builder builder = AnoModeloProtoList.newBuilder();
        anoModelos.forEach(a -> builder.addAnoModelos(ProtoConverter.modelToProto(a)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        AnoModelo result = this.dao.salvar(ProtoConverter.protoToModel(request));
        AnoModeloResult anoModeloResult = AnoModeloResult.newBuilder()
                .setCodigo(200)
                .setAnoModelo(ProtoConverter.modelToProto(result))
                .build();
        responseObserver.onNext(anoModeloResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        AnoModelo anoModelo = dao.atualizar(ProtoConverter.protoToModel(request));
        responseObserver.onNext(AnoModeloResult
                .newBuilder()
                .setAnoModelo(
                        anoModelo != null ? ProtoConverter.modelToProto(anoModelo) : AnoModeloProto.newBuilder().build()
                )
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(AnoModeloResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        AnoModelo anoModelo = dao.buscar(request.getId());
        responseObserver.onNext(AnoModeloResult
                .newBuilder()
                .setAnoModelo(
                        anoModelo != null ? ProtoConverter.modelToProto(anoModelo) : AnoModeloProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }
}
