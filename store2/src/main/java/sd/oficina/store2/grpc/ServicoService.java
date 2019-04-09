package sd.oficina.store2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.shared.proto.customer.ServicoProto;
import sd.oficina.shared.proto.customer.ServicoProtoList;
import sd.oficina.shared.proto.customer.ServicoResult;
import sd.oficina.shared.proto.customer.ServicoServiceGrpc;
import sd.oficina.store2.daos.ServicoDao;

import java.util.List;

public class ServicoService extends ServicoServiceGrpc.ServicoServiceImplBase {

    private ServicoDao servicoDao;

    public ServicoService() {
        this.servicoDao = new ServicoDao();
    }

    @Override
    public void salvar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        Servico servico = this.servicoDao.salvar(ProtoConverterStore.protoToModel(request));

        if (!servico.equals(null)) {
            responseObserver.onNext(
                    ServicoResult.newBuilder()
                            .setCodigo(200)
                            .setServico(ProtoConverterStore.modelToProto(servico))
                            .build());
        } else {
            responseObserver.onNext(
                    ServicoResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        Servico servico = this.servicoDao.atualizar(ProtoConverterStore.protoToModel(request));

        if (!servico.equals(null)) {
            responseObserver
                    .onNext(ServicoResult.newBuilder()
                            .setCodigo(200)
                            .setServico(ProtoConverterStore.modelToProto(servico)).build());
        } else {
            responseObserver
                    .onNext(ServicoResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deletar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        this.servicoDao.deletar(ProtoConverterStore.protoToModel(request));
        responseObserver.
                onNext(ServicoResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(ServicoProto request, StreamObserver<ServicoResult> responseObserver) {
        Servico servico = this.servicoDao.buscar(ProtoConverterStore.protoToModel(request));

        if (!servico.equals(null)) {
            responseObserver.onNext(ServicoResult
                    .newBuilder()
                    .setCodigo(200)
                    .setServico(
                            ProtoConverterStore.modelToProto(servico))
                    .build());
        } else {
            responseObserver.onNext(ServicoResult
                    .newBuilder()
                    .setCodigo(400)
                    .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<ServicoProtoList> responseObserver) {
        List<Servico> servicos = this.servicoDao.listar();

        ServicoProtoList.Builder builder = ServicoProtoList.newBuilder();
        for (Servico servico : servicos) {
            builder.addServico(ProtoConverterStore.modelToProto(servico));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
