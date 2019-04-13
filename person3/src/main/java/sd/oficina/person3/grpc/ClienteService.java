package sd.oficina.person3.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.person3.daos.ClienteDao;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.proto.person.ClienteList;
import sd.oficina.shared.proto.person.ClienteProto;
import sd.oficina.shared.proto.person.ClienteResult;
import sd.oficina.shared.proto.person.ClienteServiceGrpc;

import java.util.List;

public class ClienteService extends ClienteServiceGrpc.ClienteServiceImplBase {

    private ClienteDao clienteDao;

    public ClienteService() {
        this.clienteDao = new ClienteDao();
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
    }

    @Override
    public void deletar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        this.clienteDao.deletar(ProtoConverterPerson.protoToModel(request));
        responseObserver.
                onNext(ClienteResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
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
    }
}
