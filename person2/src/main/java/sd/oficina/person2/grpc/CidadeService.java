package sd.oficina.person2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.person2.dao.CidadeDao;
import sd.oficina.person2.infra.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.proto.person.CidadeList;
import sd.oficina.shared.proto.person.CidadeProto;
import sd.oficina.shared.proto.person.CidadeResult;
import sd.oficina.shared.proto.person.CidadeServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

public class CidadeService extends CidadeServiceGrpc.CidadeServiceImplBase {

    private CidadeDao dao;

    private final RedisTemplate<String, Cidade> redisTemplate;
    private final HashOperations<String, Object, Cidade> hashOperations;

    public CidadeService() {
        this.dao = new CidadeDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        Cidade resultado = this.dao.salvar(ProtoConverterPerson.protoToModel(request));
        //
        CidadeResult cidade = CidadeResult.newBuilder()
                .setCodigo(200)
                .setCidade(ProtoConverterPerson.modelToProto(resultado))
                .build();
        //
        responseObserver.onNext(cidade);
        responseObserver.onCompleted();
        this.hashOperations.put(Cidade.class.getSimpleName(),resultado.getId(),resultado);
    }

    @Override
    public void atualizar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        Cidade cidade = this.dao.atualizar(ProtoConverterPerson.protoToModel(request));
        //
        responseObserver.onNext(CidadeResult
                .newBuilder()
                .setCidade(
                        cidade != null ? ProtoConverterPerson.modelToProto(cidade) : CidadeProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
        this.hashOperations.put(Cidade.class.getSimpleName(),cidade.getId(),cidade);
    }

    @Override
    public void deletar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(CidadeResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Cidade.class.getSimpleName(),request.getId());
    }

    @Override
    public void buscar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        Cidade cidade = this.dao.getById(request.getId());
        responseObserver.onNext(CidadeResult
                .newBuilder()
                .setCidade(
                        cidade != null ? ProtoConverterPerson.modelToProto(cidade) : CidadeProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<CidadeList> responseObserver) {
        List<Cidade> cidades = this.dao.getAll();
        //
        CidadeList.Builder builder = CidadeList.newBuilder();
        for (Cidade cidade : cidades) {
            builder.addCidades(ProtoConverterPerson.modelToProto(cidade));
        }
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
