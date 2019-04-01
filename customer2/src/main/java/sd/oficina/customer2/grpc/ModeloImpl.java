package sd.oficina.customer2.grpc;

import sd.oficina.customer2.dao.ModeloDAO;
import sd.oficina.shared.model.customer.Modelo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverter;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.proto.ModeloProto;
import sd.oficina.shared.proto.ModeloProtoList;
import sd.oficina.shared.proto.ModeloResult;
import sd.oficina.shared.proto.ModeloServiceGrpc;

import java.util.List;

public class ModeloImpl extends ModeloServiceGrpc.ModeloServiceImplBase {

    private ModeloDAO dao;

    public ModeloImpl(){
        dao = new ModeloDAO();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<ModeloProtoList> responseObserver) {
        List<Modelo> modelos = dao.todos();
        final ModeloProtoList.Builder builder = ModeloProtoList.newBuilder();
        modelos.forEach(f-> builder.addModelos(ProtoConverter.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo result = this.dao.salvar(ProtoConverter.protoToModel(request));
        ModeloResult modeloResult = ModeloResult.newBuilder()
                .setCodigo(200)
                .setModelo(ProtoConverter.modelToProto(result))
                .build();
        responseObserver.onNext(modeloResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo modelo = dao.atualizar(ProtoConverter.protoToModel(request));
        responseObserver.onNext(ModeloResult
                .newBuilder()
                .setModelo(
                        modelo != null ? ProtoConverter.modelToProto(modelo) : ModeloProto.newBuilder().build()
                )
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(ModeloResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo modelo = dao.buscar(request.getId());
        responseObserver.onNext(ModeloResult
                .newBuilder()
                .setModelo(
                        modelo != null ? ProtoConverter.modelToProto(modelo) : ModeloProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }
}
