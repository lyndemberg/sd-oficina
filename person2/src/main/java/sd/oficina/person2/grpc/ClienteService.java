package sd.oficina.person2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.person2.dao.ClienteDao;
import sd.oficina.person2.infra.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.proto.person.ClienteList;
import sd.oficina.shared.proto.person.ClienteProto;
import sd.oficina.shared.proto.person.ClienteResult;
import sd.oficina.shared.proto.person.ClienteServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteService extends ClienteServiceGrpc.ClienteServiceImplBase{

    private ClienteDao dao;

    private final RedisTemplate<String, Cliente> redisTemplate;
    private final HashOperations<String, Object, Cliente> hashOperations;

    public ClienteService() {
        this.dao = new ClienteDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        Cliente cliente = this.dao.salvar(ProtoConverterPerson.protoToModel(request));
        //
        ClienteResult result = ClienteResult.newBuilder()
                .setCodigo(200)
                .setCliente(ProtoConverterPerson.modelToProto(cliente))
                .build();
        //
        responseObserver.onNext(result);
        responseObserver.onCompleted();
        this.hashOperations.put(Cliente.class.getSimpleName(),cliente.getId(),cliente);
    }

    @Override
    public void deletar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(ClienteResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Cliente.class.getSimpleName(),request.getId());
    }

    @Override
    public void atualizar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        Cliente cliente = this.dao.atualizar(ProtoConverterPerson.protoToModel(request));
        //
        responseObserver.onNext(ClienteResult
                .newBuilder()
                .setCliente(
                        cliente != null ? ProtoConverterPerson.modelToProto(cliente) : ClienteProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
        this.hashOperations.put(Cliente.class.getSimpleName(),cliente.getId(),cliente);
    }

    @Override
    public void buscar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        Cliente cliente = this.dao.getById(request.getId());
        responseObserver.onNext(ClienteResult
                .newBuilder()
                .setCliente(
                        cliente != null ? ProtoConverterPerson.modelToProto(cliente) : ClienteProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<ClienteList> responseObserver) {
        List<Cliente> clientes = this.dao.getAll();
        //
        ClienteList.Builder builder = ClienteList.newBuilder();
        for (Cliente cliente : clientes) {
            builder.addClientes(ProtoConverterPerson.modelToProto(cliente));
        }
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
