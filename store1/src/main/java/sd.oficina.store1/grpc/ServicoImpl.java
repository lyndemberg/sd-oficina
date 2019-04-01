package sd.oficina.store1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.proto.customer.ServicoProto;
import sd.oficina.shared.proto.customer.ServicoProtoList;
import sd.oficina.shared.proto.customer.ServicoResult;
import sd.oficina.shared.proto.customer.ServicoServiceGrpc;
import sd.oficina.store1.dao.ServicoDAO;

public class ServicoImpl extends ServicoServiceGrpc.ServicoServiceImplBase {

    private ServicoDAO dao;

    @Override
    public void buscarTodos(Empty request, StreamObserver<ServicoProtoList> responseObserver) {
        super.buscarTodos(request, responseObserver);
    }

    @Override
    public void salvar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        super.salvar(request, responseObserver);
    }

    @Override
    public void atualizar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        super.atualizar(request, responseObserver);
    }

    @Override
    public void deletar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        super.deletar(request, responseObserver);
    }

    @Override
    public void buscar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        super.buscar(request, responseObserver);
    }
}
