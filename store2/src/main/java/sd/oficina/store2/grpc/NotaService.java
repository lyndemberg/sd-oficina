package sd.oficina.store2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.proto.customer.EstoqueProto;
import sd.oficina.shared.proto.customer.EstoqueProtoList;
import sd.oficina.shared.proto.customer.EstoqueResult;
import sd.oficina.shared.proto.customer.EstoqueServiceGrpc;
import sd.oficina.store2.daos.EstoqueDao;

import java.util.List;

public class NotaService extends EstoqueServiceGrpc.EstoqueServiceImplBase {

    private EstoqueDao estoqueDao;

    public NotaService() {
        this.estoqueDao = new EstoqueDao();
    }

    @Override
    public void salvar(EstoqueProto request, StreamObserver<EstoqueResult> responseObserver) {
        Estoque estoque = this.estoqueDao.salvar(ProtoConverterStore.protoToModel(request));

        if (!estoque.equals(null)) {
            responseObserver.onNext(
                    EstoqueResult.newBuilder()
                            .setCodigo(200)
                            .setEstoque(ProtoConverterStore.modelToProto(estoque))
                            .build());
        } else {
            responseObserver.onNext(
                    EstoqueResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(EstoqueProto request, StreamObserver<EstoqueResult> responseObserver) {
        Estoque estoque = this.estoqueDao.atualizar(ProtoConverterStore.protoToModel(request));

        if (!estoque.equals(null)) {
            responseObserver
                    .onNext(EstoqueResult.newBuilder()
                            .setCodigo(200)
                            .setEstoque(ProtoConverterStore.modelToProto(estoque)).build());
        } else {
            responseObserver
                    .onNext(EstoqueResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deletar(EstoqueProto request, StreamObserver<EstoqueResult> responseObserver) {
        this.estoqueDao.deletar(ProtoConverterStore.protoToModel(request));
        responseObserver.
                onNext(EstoqueResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(EstoqueProto request, StreamObserver<EstoqueResult> responseObserver) {
        Estoque estoque = this.estoqueDao.buscar(ProtoConverterStore.protoToModel(request));

        if (!estoque.equals(null)) {
            responseObserver.onNext(EstoqueResult
                    .newBuilder()
                    .setCodigo(200)
                    .setEstoque(
                            ProtoConverterStore.modelToProto(estoque))
                    .build());
        } else {
            responseObserver.onNext(EstoqueResult
                    .newBuilder()
                    .setCodigo(400)
                    .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<EstoqueProtoList> responseObserver) {
        List<Estoque> estoques = this.estoqueDao.listar();

        EstoqueProtoList.Builder builder = EstoqueProtoList.newBuilder();
        for (Estoque estoque : estoques) {
            builder.addEstoque(ProtoConverterStore.modelToProto(estoque));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
