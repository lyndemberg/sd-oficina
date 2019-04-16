package sd.oficina.customer2.grpc;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer2.dao.VeiculoDAO;
import sd.oficina.customer2.cache.ConnectionFactory;
import sd.oficina.shared.model.customer.Veiculo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.proto.customer.VeiculoProto;
import sd.oficina.shared.proto.customer.VeiculoProtoList;
import sd.oficina.shared.proto.customer.VeiculoResult;
import sd.oficina.shared.proto.customer.VeiculoServiceGrpc;

import java.util.List;

public class VeiculoImpl extends VeiculoServiceGrpc.VeiculoServiceImplBase {

    private VeiculoDAO dao;

    private final RedisTemplate<String, Veiculo> redisTemplate;
    private final HashOperations<String, Object, Veiculo> hashOperations;

    public VeiculoImpl() {
        this.dao = new VeiculoDAO();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<VeiculoProtoList> responseObserver) {
        List<Veiculo> veiculos = dao.todos();
        final VeiculoProtoList.Builder builder = VeiculoProtoList.newBuilder();
        veiculos.forEach(f -> builder.addVeiculos(ProtoConverterCustomer.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        Veiculo result = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        VeiculoResult veiculoResult = VeiculoResult.newBuilder()
                .setCodigo(200)
                .setVeiculo(ProtoConverterCustomer.modelToProto(result))
                .build();
        responseObserver.onNext(veiculoResult);
        responseObserver.onCompleted();
        this.hashOperations.put(Veiculo.class.getSimpleName(),result.getId(),result);
    }

    @Override
    public void atualizar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        Veiculo veiculo = dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        responseObserver.onNext(VeiculoResult
                .newBuilder()
                .setVeiculo(veiculo != null ? ProtoConverterCustomer.modelToProto(veiculo) : VeiculoProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
        this.hashOperations.put(Veiculo.class.getSimpleName(),veiculo.getId(),veiculo);
    }

    @Override
    public void deletar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(VeiculoResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Veiculo.class.getSimpleName(),request.getId());
    }

    @Override
    public void buscar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {

        Veiculo veiculo = dao.buscar(request.getId());
        responseObserver.onNext(VeiculoResult
                .newBuilder()
                .setVeiculo(veiculo != null ? ProtoConverterCustomer.modelToProto(veiculo) : VeiculoProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }
}
