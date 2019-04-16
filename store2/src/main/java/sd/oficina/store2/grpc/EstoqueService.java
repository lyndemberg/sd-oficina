package sd.oficina.store2.grpc;

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
import sd.oficina.store2.daos.EstoqueDao;
import sd.oficina.store2.cache.ConnectionFactory;

import java.util.List;

public class EstoqueService extends EstoqueServiceGrpc.EstoqueServiceImplBase {

    private EstoqueDao estoqueDao;

    private final RedisTemplate<String, Estoque> redisTemplate;
    private final HashOperations<String, Object, Estoque> hashOperations;

    public EstoqueService() {
        this.estoqueDao = new EstoqueDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
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
        this.hashOperations.put(Estoque.class.getSimpleName(),estoque.getIdPeca(),estoque);
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
        this.hashOperations.put(Estoque.class.getSimpleName(),estoque.getIdPeca(),estoque);
    }

    @Override
    public void deletar(EstoqueProto request, StreamObserver<EstoqueResult> responseObserver) {
        this.estoqueDao.deletar(ProtoConverterStore.protoToModel(request));
        responseObserver.
                onNext(EstoqueResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Estoque.class.getSimpleName(),request.getIdPeca());
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
