package sd.oficina.customer3.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer3.dao.ModeloDao;
import sd.oficina.customer3.infra.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import sd.oficina.shared.model.customer.Modelo;
import sd.oficina.shared.proto.customer.ModeloProto;
import sd.oficina.shared.proto.customer.ModeloProtoList;
import sd.oficina.shared.proto.customer.ModeloResult;
import sd.oficina.shared.proto.customer.ModeloServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

public class ModeloService extends ModeloServiceGrpc.ModeloServiceImplBase {

    private ModeloDao dao;

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;

    public ModeloService() {
        this.dao = new ModeloDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo resultado = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        //
        ModeloResult modelo = ModeloResult.newBuilder()
                .setCodigo(200)
                .setModelo(ProtoConverterCustomer.modelToProto(resultado))
                .build();
        //
        responseObserver.onNext(modelo);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo modelo = this.dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        //
        responseObserver.onNext(ModeloResult
                .newBuilder()
                .setModelo(
                        modelo != null ? ProtoConverterCustomer.modelToProto(modelo) : ModeloProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(ModeloResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo modelo = this.dao.getById(request.getId());
        responseObserver.onNext(ModeloResult
                .newBuilder()
                .setModelo(
                        modelo != null ? ProtoConverterCustomer.modelToProto(modelo) : ModeloProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();

        // Se encontrou o Modelo
        if (modelo != null) {
            // Atualiza o cache
            hashOperations.put(Modelo.class.getSimpleName(), modelo.getId(), modelo);
        }
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<ModeloProtoList> responseObserver) {
        List<Modelo> anos = this.dao.getAll();
        //
        final ModeloProtoList.Builder builder = ModeloProtoList.newBuilder();
        anos.forEach(a -> builder.addModelos(ProtoConverterCustomer.modelToProto(a)));
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Modelo.class.getSimpleName(),
                anos.stream().collect(
                        Collectors.toMap(Modelo::getId, modelo -> modelo)
                ));
    }
}
