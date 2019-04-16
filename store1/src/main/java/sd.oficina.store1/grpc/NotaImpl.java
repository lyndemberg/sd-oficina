package sd.oficina.store1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Nota;
import sd.oficina.shared.proto.customer.NotaProto;
import sd.oficina.shared.proto.customer.NotaProtoList;
import sd.oficina.shared.proto.customer.NotaResult;
import sd.oficina.shared.proto.customer.NotaServiceGrpc;
import sd.oficina.store1.dao.NotaDAO;
import sd.oficina.store1.cache.ConnectionFactory;

import java.util.List;
import java.util.stream.Collectors;

public class NotaImpl extends NotaServiceGrpc.NotaServiceImplBase {

    private NotaDAO dao;

    private final RedisTemplate<String, Nota> redisTemplate;
    private final HashOperations<String, Object, Nota> hashOperations;

    public NotaImpl() {
        dao = new NotaDAO();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
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
        this.hashOperations.put(Nota.class.getSimpleName(),result.getId(),result);
    }

    @Override
    public void atualizar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        Nota nota = dao.atualizar(ProtoConverterStore.protoToModel(request));
        responseObserver.onNext(NotaResult
                .newBuilder()
                .setNota(
                        nota != null ? ProtoConverterStore.modelToProto(nota) :
                                NotaProto.newBuilder().build()
                )
                .setCodigo(200)
                .build());
        responseObserver.onCompleted();
        this.hashOperations.put(Nota.class.getSimpleName(),nota.getId(),nota);
    }

    @Override
    public void deletar(NotaProto request, StreamObserver<NotaResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(NotaResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Nota.class.getSimpleName(),request.getId());
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

        // Se encontrou a Nota
        if (Estoque != null) {
            // Atualiza o cache
            hashOperations.put(Nota.class.getSimpleName(), Estoque.getId(), Estoque);
        }
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<NotaProtoList> responseObserver) {
        List<Nota> notas = dao.buscarTodos();
        final NotaProtoList.Builder builder = NotaProtoList.newBuilder();
        notas.forEach(f -> builder.addNota(ProtoConverterStore.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Nota.class.getSimpleName(),
                notas.stream().collect(
                        Collectors.toMap(Nota::getId, nota -> nota)
                ));
    }
}
