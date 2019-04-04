package sd.oficina.customer3.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.customer3.dao.ModeloDao;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import sd.oficina.shared.model.customer.Modelo;
import sd.oficina.shared.proto.customer.ModeloProto;
import sd.oficina.shared.proto.customer.ModeloProtoList;
import sd.oficina.shared.proto.customer.ModeloResult;
import sd.oficina.shared.proto.customer.ModeloServiceGrpc;

import java.util.List;

public class ModeloService extends ModeloServiceGrpc.ModeloServiceImplBase {

    private ModeloDao dao;

    public ModeloService() {
        this.dao = new ModeloDao();
    }

    @Override
    public void salvar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo resultado = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        //
        ModeloResult modelo = ModeloResult.newBuilder()
                .setCodigo(200)
                .setModelo(ProtoConverterCustomer.modelToProto(resultado))
                .build();
        //
        responseObserver.onNext(modelo);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo modelo = this.dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        //
        responseObserver.onNext(ModeloResult
                .newBuilder()
                .setModelo(
                        modelo != null ? ProtoConverterCustomer.modelToProto(modelo) : ModeloProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(ModeloResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo modelo = this.dao.getById(request.getId());
        responseObserver.onNext(ModeloResult
                .newBuilder()
                .setModelo(
                        modelo != null ? ProtoConverterCustomer.modelToProto(modelo) : ModeloProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<ModeloProtoList> responseObserver) {
        List<Modelo> anos = this.dao.getAll();
        //
        final ModeloProtoList.Builder builder = ModeloProtoList.newBuilder();
        anos.forEach(a -> builder.addModelos(ProtoConverterCustomer.modelToProto(a)));
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
