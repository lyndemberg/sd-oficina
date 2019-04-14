package sd.oficina.customer3.grpc;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer3.dao.AnoModeloDao;
import sd.oficina.customer3.infra.cache.ConnectionFactory;
import sd.oficina.shared.model.customer.AnoModelo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.proto.customer.*;

import java.util.List;
import java.util.stream.Collectors;

public class AnoModeloService extends AnoModeloServiceGrpc.AnoModeloServiceImplBase{

    private AnoModeloDao dao;

    private final RedisTemplate<String, AnoModelo> redisTemplate;
    private final HashOperations<String, Object, AnoModelo> hashOperations;

    public AnoModeloService() {
        this.dao = new AnoModeloDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        AnoModelo resultado = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        //
        AnoModeloResult anoModelo = AnoModeloResult.newBuilder()
                .setCodigo(200)
                .setAnoModelo(ProtoConverterCustomer.modelToProto(resultado))
                .build();
        //
        responseObserver.onNext(anoModelo);
        responseObserver.onCompleted();
        this.hashOperations.put(AnoModelo.class.getSimpleName(),resultado.getId(),resultado);
    }

    @Override
    public void deletar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(AnoModeloResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
        this.hashOperations.delete(AnoModelo.class.getSimpleName(),request.getId());
    }

    @Override
    public void atualizar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        AnoModelo anoModelo = this.dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        //
        responseObserver.onNext(AnoModeloResult
                .newBuilder()
                .setAnoModelo(
                        anoModelo != null ? ProtoConverterCustomer.modelToProto(anoModelo) : AnoModeloProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
        this.hashOperations.put(AnoModelo.class.getSimpleName(),anoModelo.getId(),anoModelo);
    }

    @Override
    public void buscar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        AnoModelo anoModelo = this.dao.getById(request.getId());
        responseObserver.onNext(AnoModeloResult
                .newBuilder()
                .setAnoModelo(
                        anoModelo != null ? ProtoConverterCustomer.modelToProto(anoModelo) : AnoModeloProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<AnoModeloProtoList> responseObserver) {
        List<AnoModelo> anos = this.dao.getAll();
        //
        final AnoModeloProtoList.Builder builder = AnoModeloProtoList.newBuilder();
        anos.forEach(a -> builder.addAnoModelos(ProtoConverterCustomer.modelToProto(a)));
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
