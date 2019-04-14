package sd.oficina.person1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.person1.daos.FornecedorDao;
import sd.oficina.person1.infra.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Fornecedor;
import sd.oficina.shared.proto.person.*;

import java.util.List;
import java.util.stream.Collectors;

public class FornecedorService extends FornecedorServiceGrpc.FornecedorServiceImplBase {

    private FornecedorDao fornecedorDao;

    private final RedisTemplate<String, Fornecedor> redisTemplate;
    private final HashOperations<String, Object, Fornecedor> hashOperations;

    public FornecedorService() {
        this.fornecedorDao = new FornecedorDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.fornecedorDao.salvar(ProtoConverterPerson.protoToModel(request));

        if (!fornecedor.equals(null)) {
            responseObserver.onNext(
                    FornecedorResult.newBuilder()
                            .setCodigo(200)
                            .setFornecedor(ProtoConverterPerson.modelToProto(fornecedor))
                            .build());
        } else {
            responseObserver.onNext(
                    FornecedorResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
        this.hashOperations.put(Fornecedor.class.getSimpleName(),fornecedor.getId(),fornecedor);
    }

    @Override
    public void atualizar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.fornecedorDao.atualizar(ProtoConverterPerson.protoToModel(request));

        if (!fornecedor.equals(null)) {
            responseObserver
                    .onNext(FornecedorResult.newBuilder()
                            .setCodigo(200)
                            .setFornecedor(ProtoConverterPerson.modelToProto(fornecedor))
                            .build());
        } else {
            responseObserver
                    .onNext(FornecedorResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
        this.hashOperations.put(Fornecedor.class.getSimpleName(),fornecedor.getId(),fornecedor);
    }

    @Override
    public void deletar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        this.fornecedorDao.deletar(ProtoConverterPerson.protoToModel(request));
        responseObserver.
                onNext(FornecedorResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Fornecedor.class.getSimpleName(),request.getId());
    }

    @Override
    public void buscar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.fornecedorDao.buscar(ProtoConverterPerson.protoToModel(request));

        if (!fornecedor.equals(null)) {
            responseObserver.onNext(FornecedorResult
                    .newBuilder()
                    .setCodigo(200)
                    .setFornecedor(
                            ProtoConverterPerson.modelToProto(fornecedor))
                    .build());

        } else {
            responseObserver.onNext(FornecedorResult
                    .newBuilder()
                    .setCodigo(400)
                    .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<FornecedorList> responseObserver) {
        List<Fornecedor> fornecedores = this.fornecedorDao.listar();

        FornecedorList.Builder builder = FornecedorList.newBuilder();
        for (Fornecedor fornecedor : fornecedores) {
            builder.addFornecedores(ProtoConverterPerson.modelToProto(fornecedor));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
