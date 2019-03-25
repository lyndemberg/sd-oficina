package br.edu.ifpb.grpc;

import br.edu.ifpb.dao.VeiculoDAO;
import br.edu.ifpb.model.Fabricante;
import br.edu.ifpb.model.Veiculo;
import br.edu.ifpb.proto.*;
import com.google.protobuf.Empty;
import converter.ProtoConverter;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class VeiculoImpl extends VeiculoServiceGrpc.VeiculoServiceImplBase {

    private VeiculoDAO dao;

    public VeiculoImpl() {
        this.dao = new VeiculoDAO();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<VeiculoProtoList> responseObserver) {
        List<Veiculo> veiculos = dao.todos();
        final VeiculoProtoList.Builder builder = VeiculoProtoList.newBuilder();
        veiculos.forEach(f -> builder.addVeiculos(ProtoConverter.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
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
        Veiculo veiculo = dao.atualizar(ProtoConverter.protoToModel(request));
        responseObserver.onNext(VeiculoResult
                .newBuilder()
                .setVeiculo(veiculo != null ? ProtoConverter.modelToProto(veiculo) : VeiculoProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(VeiculoResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {

        Veiculo veiculo = dao.buscar(request.getId());
        responseObserver.onNext(VeiculoResult
                .newBuilder()
                .setVeiculo(veiculo != null ? ProtoConverter.modelToProto(veiculo) : VeiculoProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }
}
