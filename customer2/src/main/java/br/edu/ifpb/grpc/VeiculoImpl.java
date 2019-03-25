package br.edu.ifpb.grpc;

import br.edu.ifpb.dao.VeiculoDAO;
import br.edu.ifpb.model.Veiculo;
import br.edu.ifpb.proto.VeiculoProto;
import br.edu.ifpb.proto.VeiculoResult;
import br.edu.ifpb.proto.VeiculoServiceGrpc;
import converter.ProtoConverter;
import io.grpc.stub.StreamObserver;

public class VeiculoImpl extends VeiculoServiceGrpc.VeiculoServiceImplBase {

    private VeiculoDAO dao;

    public VeiculoImpl() {
        this.dao = new VeiculoDAO();
    }

    @Override
    public void salvar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        Veiculo result = this.dao.salvar(ProtoConverter.protoToModel(request));
        VeiculoResult veiculoResult = VeiculoResult.newBuilder()
                .setCodigo(200)
                .setVeiculo(ProtoConverter.modelToProto(result))
                .build();
        responseObserver.onNext(veiculoResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        super.atualizar(request, responseObserver);
    }

    @Override
    public void deletar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        super.deletar(request, responseObserver);
    }

    @Override
    public void buscar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        super.buscar(request, responseObserver);
    }
}
