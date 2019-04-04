package sd.oficina.oficinawebapp.person.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Estado;
import sd.oficina.shared.proto.person.EstadoProto;
import sd.oficina.shared.proto.person.EstadoServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class EstadoClient {

    private ManagedChannel channel;

    public EstadoClient() {
        this.channel = ManagedChannelBuilder
                .forAddress("localhost", 3333)
                .usePlaintext()
                .build();
    }

    public Estado salvar (Estado estado) {
        EstadoProto estadoProto =
                EstadoServiceGrpc.
                        newBlockingStub(channel)
                .salvar(ProtoConverterPerson
                        .modelToProto(estado))
                        .getEstado();

        Estado estadoPersistido = ProtoConverterPerson.protoToModel(estadoProto);

        return estadoPersistido;
    }

    public Estado atualizar(Estado estado) {
        Estado estadoAtualizado =
                ProtoConverterPerson
                        .protoToModel(EstadoServiceGrpc
                                .newBlockingStub(channel)
                                .atualizar(ProtoConverterPerson
                                        .modelToProto(estado))
                                .getEstado());

        return estadoAtualizado;
    }

    public void deletar(int idDoEstado) {
        EstadoServiceGrpc
                .newBlockingStub(channel)
                .deletar(EstadoProto
                        .newBuilder().setId(idDoEstado)
                        .build());
    }

    public Estado buscar(int idDoEstado) {

        Estado estado = ProtoConverterPerson
                .protoToModel(EstadoServiceGrpc
                        .newBlockingStub(channel)
                        .buscar(EstadoProto
                                .newBuilder()
                                .setId(idDoEstado)
                                .build()).getEstado());

        return estado;
    }

    public List<Estado> listar() {
        List<EstadoProto> estadosProto =
                EstadoServiceGrpc
                        .newBlockingStub(channel)
                .listar(Empty.newBuilder()
                        .build()).getEstadosList();

        List<Estado> estados = new ArrayList<>();
        for (EstadoProto estado: estadosProto) {
            estados.add(ProtoConverterPerson.protoToModel(estado));
        }

        return estados;
    }

}
