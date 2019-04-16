package sd.oficina.person1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.person1.daos.EstadoDao;
import sd.oficina.person1.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Estado;
import sd.oficina.shared.proto.person.*;

import java.util.List;
import java.util.stream.Collectors;

public class EstadoService extends EstadoServiceGrpc.EstadoServiceImplBase {

    private EstadoDao estadoDao;

    private final RedisTemplate<String, Estado> redisTemplate;
    private final HashOperations<String, Object, Estado> hashOperations;

    public EstadoService() {
        this.estadoDao = new EstadoDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(EstadoProto request, StreamObserver<EstadoResult> responseObserver) {
        Estado estado = this.estadoDao.salvar(ProtoConverterPerson.protoToModel(request));

        if (!estado.equals(null)) {
            responseObserver.onNext(
                    EstadoResult.newBuilder()
                            .setCodigo(200)
                            .setEstado(ProtoConverterPerson.modelToProto(estado))
                            .build());
        } else {
            responseObserver.onNext(
                    EstadoResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
        this.hashOperations.put(Estado.class.getSimpleName(),estado.getId(),estado);
    }

    @Override
    public void atualizar(EstadoProto request, StreamObserver<EstadoResult> responseObserver) {
        Estado estado = this.estadoDao.atualizar(ProtoConverterPerson.protoToModel(request));

        if (!estado.equals(null)) {
            responseObserver
                    .onNext(EstadoResult.newBuilder()
                            .setCodigo(200)
                            .setEstado(ProtoConverterPerson.modelToProto(estado))
                            .build());
        } else {
            responseObserver
                    .onNext(EstadoResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
        this.hashOperations.put(Estado.class.getSimpleName(),estado.getId(),estado);
    }

    @Override
    public void deletar(EstadoProto request, StreamObserver<EstadoResult> responseObserver) {
        this.estadoDao.deletar(ProtoConverterPerson.protoToModel(request));
        responseObserver.
                onNext(EstadoResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Estado.class.getSimpleName(), request.getId());
    }

    @Override
    public void buscar(EstadoProto request, StreamObserver<EstadoResult> responseObserver) {
        Estado estado = this.estadoDao.buscar(ProtoConverterPerson.protoToModel(request));

        if (!estado.equals(null)) {
            responseObserver.onNext(EstadoResult
                    .newBuilder()
                    .setCodigo(200)
                    .setEstado(
                            ProtoConverterPerson.modelToProto(estado))
                    .build());

            // Atualiza o cache
            hashOperations.put(Estado.class.getSimpleName(), estado.getId(), estado);

        } else {
            responseObserver.onNext(EstadoResult
                    .newBuilder()
                    .setCodigo(400)
                    .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<EstadoList> responseObserver) {
        List<Estado> estados = this.estadoDao.listar();

        EstadoList.Builder builder = EstadoList.newBuilder();
        for (Estado estado : estados) {
            builder.addEstados(ProtoConverterPerson.modelToProto(estado));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Estado.class.getSimpleName(),
                estados.stream().collect(
                        Collectors.toMap(Estado::getId, estado -> estado)
                ));
    }
}
