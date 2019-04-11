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
        super.atualizar(request, responseObserver);
    }

    @Override
    public void deletar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        super.deletar(request, responseObserver);
    }

    @Override
    public void buscar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        super.buscar(request, responseObserver);
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<NotaProtoList> responseObserver) {
        super.buscarTodos(request, responseObserver);
    }
}
