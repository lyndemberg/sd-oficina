package sd.oficina.customer2.grpc;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer2.dao.AnoModeloDAO;
import sd.oficina.customer2.infra.cache.ConnectionFactory;
import sd.oficina.shared.model.customer.AnoModelo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.proto.customer.AnoModeloProto;
import sd.oficina.shared.proto.customer.AnoModeloProtoList;
import sd.oficina.shared.proto.customer.AnoModeloResult;
import sd.oficina.shared.proto.customer.AnoModeloServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

public class AnoModeloImpl extends AnoModeloServiceGrpc.AnoModeloServiceImplBase {

    private AnoModeloDAO dao;

    private final RedisTemplate<String, AnoModelo> redisTemplate;
    private final HashOperations<String, Object, AnoModelo> hashOperations;

    public AnoModeloImpl() {
        this.dao = new AnoModeloDAO();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public void buscarTodos(Empty request, StreamObserver<AnoModeloProtoList> responseObserver) {
        List<AnoModelo> anoModelos = dao.todos();
        final AnoModeloProtoList.Builder builder = AnoModeloProtoList.newBuilder();
        anoModelos.forEach(a -> builder.addAnoModelos(ProtoConverterCustomer.modelToProto(a)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        AnoModelo result = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        AnoModeloResult anoModeloResult = AnoModeloResult.newBuilder()
                .setCodigo(200)
                .setAnoModelo(ProtoConverterCustomer.modelToProto(result))
                .build();
        responseObserver.onNext(anoModeloResult);
        responseObserver.onCompleted();
        this.hashOperations.put(AnoModelo.class.getSimpleName(),result.getId(),result);
    }

    @Override
    public void atualizar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        AnoModelo anoModelo = dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        responseObserver.onNext(AnoModeloResult
                .newBuilder()
                .setAnoModelo(
                        anoModelo != null ? ProtoConverterCustomer.modelToProto(anoModelo) : AnoModeloProto.newBuilder().build()
                )
                .build());
        responseObserver.onCompleted();
        this.hashOperations.put(AnoModelo.class.getSimpleName(),anoModelo.getId(),anoModelo);
    }

    @Override
    public void deletar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(AnoModeloResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
        this.hashOperations.delete(AnoModelo.class.getSimpleName(),request.getId());
    }

    @Override
    public void buscar(AnoModeloProto request, StreamObserver<AnoModeloResult> responseObserver) {
        AnoModelo anoModelo = dao.buscar(request.getId());
        responseObserver.onNext(AnoModeloResult
                .newBuilder()
                .setAnoModelo(
                        anoModelo != null ? ProtoConverterCustomer.modelToProto(anoModelo) : AnoModeloProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }
}
