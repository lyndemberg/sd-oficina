package sd.oficina.oficinawebapp.person.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.proto.person.ClienteProto;
import sd.oficina.shared.proto.person.ClienteServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class ClienteClient {

    private ManagedChannel channel;

    public ClienteClient() {
        this.channel = ManagedChannelBuilder
                .forAddress("localhost", 2222)
                .usePlaintext()
                .build();
    }

    public Cliente salvar (Cliente cliente) {
        ClienteProto clienteProto =
                ClienteServiceGrpc.
                        newBlockingStub(channel)
                .salvar(ProtoConverterPerson
                        .modelToProto(cliente))
                        .getCliente();

        Cliente clientePersistido = ProtoConverterPerson.protoToModel(clienteProto);

        return clientePersistido;
    }

    public Cliente atualizar(Cliente cliente) {
        Cliente clienteAtualizado =
                ProtoConverterPerson
                        .protoToModel(ClienteServiceGrpc
                                .newBlockingStub(channel)
                                .atualizar(ProtoConverterPerson
                                        .modelToProto(cliente))
                                .getCliente());

        return clienteAtualizado;
    }

    public void deletar(int idDoCliente) {
        ClienteServiceGrpc
                .newBlockingStub(channel)
                .deletar(ClienteProto
                        .newBuilder().setId(idDoCliente)
                        .build());
    }

    public Cliente buscar(int idDoCliente) {

        Cliente cliente = ProtoConverterPerson
                .protoToModel(ClienteServiceGrpc
                        .newBlockingStub(channel)
                        .buscar(ClienteProto
                                .newBuilder()
                                .setId(idDoCliente)
                                .build()).getCliente());

        return cliente;
    }

    public List<Cliente> listar() {
        List<ClienteProto> clientesProto =
                ClienteServiceGrpc
                        .newBlockingStub(channel)
                .listar(Empty.newBuilder()
                        .build()).getClientesList();

        List<Cliente> clientes = new ArrayList<>();
        for (ClienteProto cliente: clientesProto) {
            clientes.add(ProtoConverterPerson.protoToModel(cliente));
        }

        return clientes;
    }

}
