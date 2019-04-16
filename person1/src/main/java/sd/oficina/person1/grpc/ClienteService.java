package sd.oficina.person1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.person1.daos.ClienteDao;
import sd.oficina.person1.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.proto.person.*;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteService extends ClienteServiceGrpc.ClienteServiceImplBase {

    private ClienteDao clienteDao;

    private final RedisTemplate<String, Cliente> redisTemplate;
    private final HashOperations<String, Object, Cliente> hashOperations;

    public ClienteService() {
        this.clienteDao = new ClienteDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void salvar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        Cliente cliente = this.clienteDao.salvar(ProtoConverterPerson.protoToModel(request));

        if (!cliente.equals(null)) {
            responseObserver.onNext(
                    ClienteResult.newBuilder()
                            .setCodigo(200)
                            .setCliente(ProtoConverterPerson.modelToProto(cliente))
                            .build());
        } else {
            responseObserver.onNext(
                    ClienteResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
        this.hashOperations.put(Cliente.class.getSimpleName(),cliente.getId(),cliente);
    }

    @Override
    public void atualizar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        Cliente cliente = this.clienteDao.atualizar(ProtoConverterPerson.protoToModel(request));

        if (!cliente.equals(null)) {
            responseObserver
                    .onNext(ClienteResult.newBuilder()
                            .setCodigo(200)
                            .setCliente(ProtoConverterPerson.modelToProto(cliente))
                            .build());
        } else {
            responseObserver
                    .onNext(ClienteResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
        this.hashOperations.put(Cliente.class.getSimpleName(),cliente.getId(),cliente);
    }

    @Override
    public void deletar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        this.clienteDao.deletar(ProtoConverterPerson.protoToModel(request));
        responseObserver.
                onNext(ClienteResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
        this.hashOperations.delete(Cliente.class.getSimpleName(),request.getId());
    }

    @Override
    public void buscar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        Cliente cliente = this.clienteDao.buscar(ProtoConverterPerson.protoToModel(request));

        if (!cliente.equals(null)) {
            responseObserver.onNext(ClienteResult
                    .newBuilder()
                    .setCodigo(200)
                    .setCliente(
                            ProtoConverterPerson.modelToProto(cliente))
                    .build());

            // Atualiza o cache
            hashOperations.put(Cliente.class.getSimpleName(), cliente.getId(), cliente);

        } else {
            responseObserver.onNext(ClienteResult
                    .newBuilder()
                    .setCodigo(400)
                    .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<ClienteList> responseObserver) {
        List<Cliente> clientes = this.clienteDao.listar();

        ClienteList.Builder builder = ClienteList.newBuilder();
        for (Cliente cliente : clientes) {
            builder.addClientes(ProtoConverterPerson.modelToProto(cliente));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Cliente.class.getSimpleName(),
                clientes.stream().collect(
                        Collectors.toMap(Cliente::getId, cliente -> cliente)
                ));
    }
}
