package sd.oficina.customer2.grpc;

import sd.oficina.customer2.dao.VeiculoDAO;
import sd.oficina.shared.model.customer.Veiculo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.proto.customer.VeiculoProto;
import sd.oficina.shared.proto.customer.VeiculoProtoList;
import sd.oficina.shared.proto.customer.VeiculoResult;
import sd.oficina.shared.proto.customer.VeiculoServiceGrpc;

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
        veiculos.forEach(f -> builder.addVeiculos(ProtoConverterCustomer.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        Veiculo result = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        VeiculoResult veiculoResult = VeiculoResult.newBuilder()
                .setCodigo(200)
                .setVeiculo(ProtoConverterCustomer.modelToProto(result))
                .build();
        responseObserver.onNext(veiculoResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        Veiculo veiculo = dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        responseObserver.onNext(VeiculoResult
                .newBuilder()
                .setVeiculo(veiculo != null ? ProtoConverterCustomer.modelToProto(veiculo) : VeiculoProto.newBuilder().build())
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
                .setVeiculo(veiculo != null ? ProtoConverterCustomer.modelToProto(veiculo) : VeiculoProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }
}
