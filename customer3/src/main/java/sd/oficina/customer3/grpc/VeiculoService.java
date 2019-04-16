package sd.oficina.customer3.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer3.dao.VeiculoDao;
import sd.oficina.customer3.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.proto.customer.VeiculoProto;
import sd.oficina.shared.proto.customer.VeiculoProtoList;
import sd.oficina.shared.proto.customer.VeiculoResult;
import sd.oficina.shared.proto.customer.VeiculoServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

public class VeiculoService extends VeiculoServiceGrpc.VeiculoServiceImplBase {

    private VeiculoDao dao;

    private final RedisTemplate<String, Veiculo> redisTemplate;
    private final HashOperations<String, Object, Veiculo> hashOperations;

    public VeiculoService() {
        this.dao = new VeiculoDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        Veiculo resultado = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        //
        VeiculoResult modelo = VeiculoResult.newBuilder()
                .setCodigo(200)
                .setVeiculo(ProtoConverterCustomer.modelToProto(resultado))
                .build();
        //
        responseObserver.onNext(modelo);
        responseObserver.onCompleted();
        this.hashOperations.put(Veiculo.class.getSimpleName(),resultado.getId(),resultado);
    }

    @Override
    public void deletar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        super.deletar(request, responseObserver);
    }

    @Override
    public void atualizar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        Veiculo veiculo = this.dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        //
        responseObserver.onNext(VeiculoResult
                .newBuilder()
                .setVeiculo(
                        veiculo != null ? ProtoConverterCustomer.modelToProto(veiculo) : VeiculoProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
        this.hashOperations.put(Veiculo.class.getSimpleName(),veiculo.getId(),veiculo);
    }

    @Override
    public void buscar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {
        Veiculo veiculo = this.dao.getById(request.getId());
        responseObserver.onNext(VeiculoResult
                .newBuilder()
                .setVeiculo(
                        veiculo != null ? ProtoConverterCustomer.modelToProto(veiculo) : VeiculoProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();

        // Se encontrou o veiculo
        if (veiculo != null) {
            // Atualiza o cache
            hashOperations.put(Veiculo.class.getSimpleName(), veiculo.getId(), veiculo);
        }
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<VeiculoProtoList> responseObserver) {
        List<Veiculo> anos = this.dao.getAll();
        //
        final VeiculoProtoList.Builder builder = VeiculoProtoList.newBuilder();
        anos.forEach(a -> builder.addVeiculos(ProtoConverterCustomer.modelToProto(a)));
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Veiculo.class.getSimpleName(),
                anos.stream().collect(
                        Collectors.toMap(Veiculo::getId, veiculo -> veiculo)
                ));
    }
}
