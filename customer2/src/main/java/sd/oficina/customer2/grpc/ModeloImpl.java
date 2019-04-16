package sd.oficina.customer2.grpc;

import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer2.dao.ModeloDAO;
import sd.oficina.customer2.cache.ConnectionFactory;
import sd.oficina.shared.model.customer.Modelo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import sd.oficina.shared.proto.customer.ModeloProto;
import sd.oficina.shared.proto.customer.ModeloProtoList;
import sd.oficina.shared.proto.customer.ModeloResult;
import sd.oficina.shared.proto.customer.ModeloServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

public class ModeloImpl extends ModeloServiceGrpc.ModeloServiceImplBase {

    private ModeloDAO dao;

    private final RedisTemplate<String, Modelo> redisTemplate;
    private final HashOperations<String, Object, Modelo> hashOperations;

    public ModeloImpl(){
        dao = new ModeloDAO();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<ModeloProtoList> responseObserver) {
        List<Modelo> modelos = dao.todos();
        final ModeloProtoList.Builder builder = ModeloProtoList.newBuilder();
        modelos.forEach(f-> builder.addModelos(ProtoConverterCustomer.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Modelo.class.getSimpleName(),
                modelos.stream().collect(
                        Collectors.toMap(Modelo::getId, modelo -> modelo)
                ));
    }

    @Override
    public void salvar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo result = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        ModeloResult modeloResult = ModeloResult.newBuilder()
                .setCodigo(200)
                .setModelo(ProtoConverterCustomer.modelToProto(result))
                .build();
        responseObserver.onNext(modeloResult);
        responseObserver.onCompleted();
        this.hashOperations.put(Modelo.class.getSimpleName(),result.getId(),result);
    }

    @Override
    public void atualizar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo modelo = dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        responseObserver.onNext(ModeloResult
                .newBuilder()
                .setModelo(
                        modelo != null ? ProtoConverterCustomer.modelToProto(modelo) : ModeloProto.newBuilder().build()
                )
                .build());
        responseObserver.onCompleted();
        this.hashOperations.put(Modelo.class.getSimpleName(),modelo.getId(),modelo);
    }

    @Override
    public void deletar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(ModeloResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Modelo.class.getSimpleName(),request.getId());
    }

    @Override
    public void buscar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {
        Modelo modelo = dao.buscar(request.getId());
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
}
