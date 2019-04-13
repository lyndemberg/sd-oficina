package sd.oficina.person3.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.person3.daos.EstadoDao;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Estado;
import sd.oficina.shared.proto.person.EstadoList;
import sd.oficina.shared.proto.person.EstadoProto;
import sd.oficina.shared.proto.person.EstadoResult;
import sd.oficina.shared.proto.person.EstadoServiceGrpc;

import java.util.List;

public class EstadoService extends EstadoServiceGrpc.EstadoServiceImplBase {

    private EstadoDao estadoDao;

    public EstadoService() {
        this.estadoDao = new EstadoDao();
    }

    @Override
    public void salvar(EstadoProto request, StreamObserver<EstadoResult> responseObserver) {
        Estado estado = this.estadoDao.salvar(ProtoConverterPerson.protoToModel(request));

        if (!estado.equals(null)) {
            responseObserver.onNext(
                    EstadoResult.newBuilder()
                            .setCodigo(200)
                            .setEstado(ProtoConverterPerson.modelToProto(estado))
                            .build());
        } else {
            responseObserver.onNext(
                    EstadoResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(EstadoProto request, StreamObserver<EstadoResult> responseObserver) {
        Estado estado = this.estadoDao.atualizar(ProtoConverterPerson.protoToModel(request));

        if (!estado.equals(null)) {
            responseObserver
                    .onNext(EstadoResult.newBuilder()
                            .setCodigo(200)
                            .setEstado(ProtoConverterPerson.modelToProto(estado))
                            .build());
        } else {
            responseObserver
                    .onNext(EstadoResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deletar(EstadoProto request, StreamObserver<EstadoResult> responseObserver) {
        this.estadoDao.deletar(ProtoConverterPerson.protoToModel(request));
        responseObserver.
                onNext(EstadoResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(EstadoProto request, StreamObserver<EstadoResult> responseObserver) {
        Estado estado = this.estadoDao.buscar(ProtoConverterPerson.protoToModel(request));

        if (!estado.equals(null)) {
            responseObserver.onNext(EstadoResult
                    .newBuilder()
                    .setCodigo(200)
                    .setEstado(
                            ProtoConverterPerson.modelToProto(estado))
                    .build());
        } else {
            responseObserver.onNext(EstadoResult
                    .newBuilder()
                    .setCodigo(400)
                    .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<EstadoList> responseObserver) {
        List<Estado> estados = this.estadoDao.listar();

        EstadoList.Builder builder = EstadoList.newBuilder();
        for (Estado estado : estados) {
            builder.addEstados(ProtoConverterPerson.modelToProto(estado));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
