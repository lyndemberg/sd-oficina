package sd.oficina.oficinawebapp.person.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Fornecedor;
import sd.oficina.shared.proto.person.FornecedorProto;
import sd.oficina.shared.proto.person.FornecedorServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class FornecedorClient {

    private ManagedChannel channel;

    public FornecedorClient() {
        this.channel = ManagedChannelBuilder
                .forAddress("localhost", 2222)
                .usePlaintext()
                .build();
    }

    public Fornecedor salvar (Fornecedor fornecedor) {
        FornecedorProto fornecedorProto =
                FornecedorServiceGrpc.
                        newBlockingStub(channel)
                .salvar(ProtoConverterPerson
                        .modelToProto(fornecedor))
                        .getFornecedor();

        Fornecedor fornecedorPersistido = ProtoConverterPerson.protoToModel(fornecedorProto);

        return fornecedorPersistido;
    }

    public Fornecedor atualizar(Fornecedor fornecedor) {
        Fornecedor fornecedorAtualizado =
                ProtoConverterPerson
                        .protoToModel(FornecedorServiceGrpc
                                .newBlockingStub(channel)
                                .atualizar(ProtoConverterPerson
                                        .modelToProto(fornecedor))
                                .getFornecedor());

        return fornecedorAtualizado;
    }

    public void deletar(int idDoFornecedor) {
        FornecedorServiceGrpc
                .newBlockingStub(channel)
                .deletar(FornecedorProto
                        .newBuilder().setId(idDoFornecedor)
                        .build());
    }

    public Fornecedor buscar(int idDoFornecedor) {

        Fornecedor fornecedor = ProtoConverterPerson
                .protoToModel(FornecedorServiceGrpc
                        .newBlockingStub(channel)
                        .buscar(FornecedorProto
                                .newBuilder()
                                .setId(idDoFornecedor)
                                .build()).getFornecedor());

        return fornecedor;
    }

    public List<Fornecedor> listar() {
        List<FornecedorProto> fornecedoresProto =
                FornecedorServiceGrpc
                        .newBlockingStub(channel)
                .listar(Empty.newBuilder()
                        .build()).getFornecedoresList();

        List<Fornecedor> fornecedores = new ArrayList<>();
        for (FornecedorProto fornecedor: fornecedoresProto) {
            fornecedores.add(ProtoConverterPerson.protoToModel(fornecedor));
        }

        return fornecedores;
    }

}
