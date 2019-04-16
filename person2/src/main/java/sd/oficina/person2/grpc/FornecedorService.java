package sd.oficina.person2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.person2.dao.FornecedorDao;
import sd.oficina.person2.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Fornecedor;
import sd.oficina.shared.proto.person.FornecedorList;
import sd.oficina.shared.proto.person.FornecedorProto;
import sd.oficina.shared.proto.person.FornecedorResult;
import sd.oficina.shared.proto.person.FornecedorServiceGrpc;

import java.util.List;

public class FornecedorService extends FornecedorServiceGrpc.FornecedorServiceImplBase {

    private FornecedorDao dao;

    private final RedisTemplate<String, Fornecedor> redisTemplate;
    private final HashOperations<String, Object, Fornecedor> hashOperations;

    public FornecedorService() {
        this.dao = new FornecedorDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor resultado = this.dao.salvar(ProtoConverterPerson.protoToModel(request));
        //
        FornecedorResult estado = FornecedorResult.newBuilder()
                .setCodigo(200)
                .setFornecedor(ProtoConverterPerson.modelToProto(resultado))
                .build();
        //
        responseObserver.onNext(estado);
        responseObserver.onCompleted();
        this.hashOperations.put(Fornecedor.class.getSimpleName(),resultado.getId(),resultado);
    }

    @Override
    public void deletar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(FornecedorResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Fornecedor.class.getSimpleName(),request.getId());
    }

    @Override
    public void atualizar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.dao.atualizar(ProtoConverterPerson.protoToModel(request));
        //
        responseObserver.onNext(FornecedorResult
                .newBuilder()
                .setFornecedor(
                        fornecedor != null ? ProtoConverterPerson.modelToProto(fornecedor) : FornecedorProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
        this.hashOperations.put(Fornecedor.class.getSimpleName(),fornecedor.getId(),fornecedor);
    }

    @Override
    public void buscar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.dao.getById(request.getId());
        responseObserver.onNext(FornecedorResult
                .newBuilder()
                .setFornecedor(
                        fornecedor != null ? ProtoConverterPerson.modelToProto(fornecedor) : FornecedorProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<FornecedorList> responseObserver) {
        List<Fornecedor> estados = this.dao.getAll();
        //
        FornecedorList.Builder builder = FornecedorList.newBuilder();
        for (Fornecedor estado : estados) {
            builder.addFornecedores(ProtoConverterPerson.modelToProto(estado));
        }
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
