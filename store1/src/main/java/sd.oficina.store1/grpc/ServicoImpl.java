package sd.oficina.store1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.shared.proto.customer.ServicoProto;
import sd.oficina.shared.proto.customer.ServicoProtoList;
import sd.oficina.shared.proto.customer.ServicoResult;
import sd.oficina.shared.proto.customer.ServicoServiceGrpc;
import sd.oficina.store1.dao.ServicoDAO;

import java.util.List;

public class ServicoImpl extends ServicoServiceGrpc.ServicoServiceImplBase {

    private ServicoDAO dao;

    public ServicoImpl() {
        dao = new ServicoDAO();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<ServicoProtoList> responseObserver) {

        List<Servico> servicos = dao.buscarTodos();
        final ServicoProtoList.Builder builder = ServicoProtoList.newBuilder();
        servicos.forEach(f -> builder.addServico(ProtoConverterStore.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {

        Servico result = this.dao.salvar(ProtoConverterStore.protoToModel(request));
        ServicoResult fabricanteResult = ServicoResult.newBuilder()
                .setCodigo(200)
                .setServico(ProtoConverterStore.modelToProto(result))
                .build();
        responseObserver.onNext(fabricanteResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        Servico servico = dao.atualizar(ProtoConverterStore.protoToModel(request));
        responseObserver.onNext(ServicoResult
                .newBuilder()
                .setServico(
                        servico != null ? ProtoConverterStore.modelToProto(servico) :
                                ServicoProto.newBuilder().build()
                )
                .setCodigo(200)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(ServicoResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        Servico servico = dao.buscar(request.getId());
        responseObserver.onNext(ServicoResult
                .newBuilder()
                .setServico(
                        servico != null ? ProtoConverterStore.modelToProto(servico) :
                                ServicoProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }
}
