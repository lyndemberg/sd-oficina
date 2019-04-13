package sd.oficina.store1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.proto.customer.EstoqueProto;
import sd.oficina.shared.proto.customer.EstoqueProtoList;
import sd.oficina.shared.proto.customer.EstoqueResult;
import sd.oficina.shared.proto.customer.EstoqueServiceGrpc;
import sd.oficina.store1.dao.EstoqueDAO;
import sd.oficina.store1.infra.cache.ConnectionFactory;

import java.util.List;
import java.util.stream.Collectors;

public class EstoqueImpl extends EstoqueServiceGrpc.EstoqueServiceImplBase {

    private EstoqueDAO dao;

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;

    public EstoqueImpl() {
        dao = new EstoqueDAO();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(EstoqueProto request, StreamObserver<EstoqueResult> responseObserver) {
        Estoque result = this.dao.salvar(ProtoConverterStore.protoToModel(request));
        EstoqueResult fabricanteResult = EstoqueResult.newBuilder()
                .setCodigo(200)
                .setEstoque(ProtoConverterStore.modelToProto(result))
                .build();
        responseObserver.onNext(fabricanteResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(EstoqueProto request, StreamObserver<EstoqueResult> responseObserver) {
        Estoque Estoque = dao.atualizar(ProtoConverterStore.protoToModel(request));
        responseObserver.onNext(EstoqueResult
                .newBuilder()
                .setEstoque(
                        Estoque != null ? ProtoConverterStore.modelToProto(Estoque) :
                                EstoqueProto.newBuilder().build()
                )
                .setCodigo(200)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(EstoqueProto request, StreamObserver<EstoqueResult> responseObserver) {
        this.dao.deletar(request.getIdPeca());
        responseObserver.onNext(EstoqueResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(EstoqueProto request, StreamObserver<EstoqueResult> responseObserver) {
        Estoque Estoque = dao.buscar(request.getIdPeca());
        responseObserver.onNext(EstoqueResult
                .newBuilder()
                .setEstoque(
                        Estoque != null ? ProtoConverterStore.modelToProto(Estoque) :
                                EstoqueProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();

        // Se encontrou o Estoque
        if (Estoque != null) {
            // Atualiza o cache
            hashOperations.put(Estoque.class.getSimpleName(), Estoque.getIdPeca(), Estoque);
        }
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<EstoqueProtoList> responseObserver) {
        List<Estoque> Estoques = dao.buscarTodos();
        final EstoqueProtoList.Builder builder = EstoqueProtoList.newBuilder();
        Estoques.forEach(f -> builder.addEstoque(ProtoConverterStore.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Estoque.class.getSimpleName(),
                Estoques.stream().collect(
                        Collectors.toMap(Estoque::getIdPeca, estoque -> estoque)
                ));
    }
}
