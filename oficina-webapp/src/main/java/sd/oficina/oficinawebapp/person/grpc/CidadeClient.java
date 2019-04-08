package sd.oficina.oficinawebapp.person.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.proto.person.CidadeProto;
import sd.oficina.shared.proto.person.CidadeServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class CidadeClient {

    private ManagedChannel channel;

    public CidadeClient() {
        this.channel = ManagedChannelBuilder
                .forAddress("localhost", 2222)
                .usePlaintext()
                .build();
    }

    public Cidade salvar (Cidade cidade) {
        CidadeProto cidadeProto =
                CidadeServiceGrpc.
                        newBlockingStub(channel)
                .salvar(ProtoConverterPerson
                        .modelToProto(cidade))
                        .getCidade();

        Cidade cidadePersistida = ProtoConverterPerson.protoToModel(cidadeProto);

        return cidadePersistida;
    }

    public Cidade atualizar(Cidade cidade) {
        Cidade cidadeAtualizada =
                ProtoConverterPerson
                        .protoToModel(CidadeServiceGrpc
                                .newBlockingStub(channel)
                                .atualizar(ProtoConverterPerson
                                        .modelToProto(cidade))
                                .getCidade());

        return cidadeAtualizada;
    }

    public void deletar(int idDaCidade) {
        CidadeServiceGrpc
                .newBlockingStub(channel)
                .deletar(CidadeProto
                        .newBuilder().setId(idDaCidade)
                        .build());
    }

    public Cidade buscar(int idDaCidade) {

        Cidade cidade = ProtoConverterPerson
                .protoToModel(CidadeServiceGrpc
                        .newBlockingStub(channel)
                        .buscar(CidadeProto
                                .newBuilder()
                                .setId(idDaCidade)
                                .build()).getCidade());

        return cidade;
    }

    public List<Cidade> listar() {
        List<CidadeProto> cidadesProto =
                CidadeServiceGrpc
                        .newBlockingStub(channel)
                .listar(Empty.newBuilder()
                        .build()).getCidadesList();

        List<Cidade> cidades = new ArrayList<>();
        for (CidadeProto cidade: cidadesProto) {
            cidades.add(ProtoConverterPerson.protoToModel(cidade));
        }

        return cidades;
    }

}
