package sd.oficina.customer2.grpc;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer2.dao.VeiculoDAO;
import sd.oficina.customer2.infra.cache.ConnectionFactory;
import sd.oficina.shared.model.customer.Veiculo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.proto.customer.VeiculoProto;
import sd.oficina.shared.proto.customer.VeiculoProtoList;
import sd.oficina.shared.proto.customer.VeiculoResult;
import sd.oficina.shared.proto.customer.VeiculoServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

public class VeiculoImpl extends VeiculoServiceGrpc.VeiculoServiceImplBase {

    private VeiculoDAO dao;

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;

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

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Veiculo.class.getSimpleName(),
                veiculos.stream().collect(
                        Collectors.toMap(Veiculo::getId, veiculo -> veiculo)
                ));
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
    }

    @Override
    public void atualizar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        Veiculo veiculo = dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        responseObserver.onNext(VeiculoResult
                .newBuilder()
                .setVeiculo(veiculo != null ? ProtoConverterCustomer.modelToProto(veiculo) : VeiculoProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(VeiculoResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {

        Veiculo veiculo = dao.buscar(request.getId());
        responseObserver.onNext(VeiculoResult
                .newBuilder()
                .setVeiculo(veiculo != null ? ProtoConverterCustomer.modelToProto(veiculo) : VeiculoProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();

        // Se encontrou o veiculo
        if (veiculo != null) {
            // Atualiza o cache
            hashOperations.put(Veiculo.class.getSimpleName(), veiculo.getId(), veiculo);
        }
    }
}
