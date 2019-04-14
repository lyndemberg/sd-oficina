package sd.oficina.store2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Nota;
import sd.oficina.shared.proto.customer.*;
import sd.oficina.store2.daos.NotaDao;
import sd.oficina.store2.infra.cache.ConnectionFactory;

import java.util.List;
import java.util.stream.Collectors;

public class NotaService extends NotaServiceGrpc.NotaServiceImplBase {

    private NotaDao notaDao;

    private final RedisTemplate<String, Nota> redisTemplate;
    private final HashOperations<String, Object, Nota> hashOperations;

    public NotaService() {
        this.notaDao = new NotaDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        Nota nota = this.notaDao.salvar(ProtoConverterStore.protoToModel(request));

        if (!nota.equals(null)) {
            responseObserver.onNext(
                    NotaResult.newBuilder()
                            .setCodigo(200)
                            .setNota(ProtoConverterStore.modelToProto(nota))
                            .build());
        } else {
            responseObserver.onNext(
                    NotaResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
        this.hashOperations.put(Nota.class.getSimpleName(),nota.getId(),nota);
    }

    @Override
    public void atualizar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        Nota nota = this.notaDao.atualizar(ProtoConverterStore.protoToModel(request));

        if (!nota.equals(null)) {
            responseObserver
                    .onNext(NotaResult.newBuilder()
                            .setCodigo(200)
                            .setNota(ProtoConverterStore.modelToProto(nota)).build());
        } else {
            responseObserver
                    .onNext(NotaResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
        this.hashOperations.put(Nota.class.getSimpleName(),nota.getId(),nota);
    }

    @Override
    public void deletar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        this.notaDao.deletar(ProtoConverterStore.protoToModel(request));
        responseObserver.
                onNext(NotaResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Nota.class.getSimpleName(),request.getId());
    }

    @Override
    public void buscar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        Nota nota = this.notaDao.buscar(ProtoConverterStore.protoToModel(request));

        if (!nota.equals(null)) {
            responseObserver.onNext(NotaResult
                    .newBuilder()
                    .setCodigo(200)
                    .setNota(
                            ProtoConverterStore.modelToProto(nota))
                    .build());

        } else {
            responseObserver.onNext(NotaResult
                    .newBuilder()
                    .setCodigo(400)
                    .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<NotaProtoList> responseObserver) {
        List<Nota> notas = this.notaDao.listar();

        NotaProtoList.Builder builder = NotaProtoList.newBuilder();
        for (Nota nota : notas) {
            builder.addNota(ProtoConverterStore.modelToProto(nota));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
