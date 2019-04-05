package sd.oficina.person2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.person2.dao.ClienteDao;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.proto.person.ClienteList;
import sd.oficina.shared.proto.person.ClienteProto;
import sd.oficina.shared.proto.person.ClienteResult;
import sd.oficina.shared.proto.person.ClienteServiceGrpc;

import java.util.List;

public class ClienteService extends ClienteServiceGrpc.ClienteServiceImplBase{

    private ClienteDao dao;

    public ClienteService() {
        this.dao = new ClienteDao();
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
    }

    @Override
    public void deletar(ClienteProto request, StreamObserver<ClienteResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(ClienteResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
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
