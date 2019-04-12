package sd.oficina.store1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Nota;
import sd.oficina.shared.proto.customer.NotaProto;
import sd.oficina.shared.proto.customer.NotaProtoList;
import sd.oficina.shared.proto.customer.NotaResult;
import sd.oficina.shared.proto.customer.NotaServiceGrpc;
import sd.oficina.store1.dao.NotaDAO;

import java.util.List;

public class NotaImpl extends NotaServiceGrpc.NotaServiceImplBase {

    private NotaDAO dao;

    public NotaImpl() {
        dao = new NotaDAO();
    }

    @Override
    public void salvar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        Nota result = this.dao.salvar(ProtoConverterStore.protoToModel(request));
        NotaResult notaResult = NotaResult.newBuilder()
                .setCodigo(200)
                .setNota(ProtoConverterStore.modelToProto(result))
                .build();
        responseObserver.onNext(notaResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        Nota Estoque = dao.atualizar(ProtoConverterStore.protoToModel(request));
        responseObserver.onNext(NotaResult
                .newBuilder()
                .setNota(
                        Estoque != null ? ProtoConverterStore.modelToProto(Estoque) :
                                NotaProto.newBuilder().build()
                )
                .setCodigo(200)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(NotaResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        Nota Estoque = dao.buscar(request.getId());
        responseObserver.onNext(NotaResult
                .newBuilder()
                .setNota(
                        Estoque != null ? ProtoConverterStore.modelToProto(Estoque) :
                                NotaProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<NotaProtoList> responseObserver) {
        List<Nota> notas = dao.buscarTodos();
        final NotaProtoList.Builder builder = NotaProtoList.newBuilder();
        notas.forEach(f -> builder.addNota(ProtoConverterStore.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
