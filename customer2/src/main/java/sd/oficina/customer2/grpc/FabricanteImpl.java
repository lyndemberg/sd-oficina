package sd.oficina.customer2.grpc;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer2.dao.FabricanteDAO;
import com.google.protobuf.Empty;
import sd.oficina.customer2.infra.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import sd.oficina.shared.model.customer.Fabricante;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.proto.customer.FabricanteProto;
import sd.oficina.shared.proto.customer.FabricanteProtoList;
import sd.oficina.shared.proto.customer.FabricanteResult;
import sd.oficina.shared.proto.customer.FabricanteServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

public class FabricanteImpl extends FabricanteServiceGrpc.FabricanteServiceImplBase {

    private FabricanteDAO dao;

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;

    public FabricanteImpl() {
        this.dao = new FabricanteDAO();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<FabricanteProtoList> responseObserver) {
        List<Fabricante> fabricantes = dao.todos();
        final FabricanteProtoList.Builder builder = FabricanteProtoList.newBuilder();
        fabricantes.forEach(f-> builder.addFabricantes(ProtoConverterCustomer.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Fabricante.class.getSimpleName(),
                fabricantes.stream().collect(
                        Collectors.toMap(Fabricante::getId, fabricante -> fabricante)
                ));
    }

    @Override
    public void salvar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        Fabricante result = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        FabricanteResult fabricanteResult = FabricanteResult.newBuilder()
                .setCodigo(200)
                .setFabricante(ProtoConverterCustomer.modelToProto(result))
                .build();
        responseObserver.onNext(fabricanteResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        Fabricante fabricante = dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        responseObserver.onNext(FabricanteResult
                .newBuilder()
                .setFabricante(
                        fabricante != null ? ProtoConverterCustomer.modelToProto(fabricante) : FabricanteProto.newBuilder().build()
                )
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(FabricanteResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {

        Fabricante fabricante = dao.buscar(request.getId());
        responseObserver.onNext(FabricanteResult
                .newBuilder()
                .setFabricante(
                        fabricante != null ? ProtoConverterCustomer.modelToProto(fabricante) : FabricanteProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();

        // Se encontrou o Fabricante
        if (fabricante != null) {
            // Atualiza o cache
            hashOperations.put(Fabricante.class.getSimpleName(), fabricante.getId(), fabricante);
        }
    }

}
