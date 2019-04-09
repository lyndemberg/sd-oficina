package sd.oficina.oficinawebapp.store.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.shared.proto.customer.ServicoProto;
import sd.oficina.shared.proto.customer.ServicoServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class ServicoClient {

    private ManagedChannel channel;

    public ServicoClient() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 1111)
                .usePlaintext()
                .build();
    }

    public List<Servico> todos() {
        ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channel);
        List<ServicoProto> lista = stub.buscarTodos(Empty.newBuilder().build()).getServicoList();
        List<Servico> Servicos = new ArrayList<>();
        lista.forEach(f -> Servicos.add(ProtoConverterStore.protoToModel(f)));
        return Servicos;
    }

    public Servico salvar(Servico Servico) {

        ServicoProto grpc = ProtoConverterStore.modelToProto(Servico);
        ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channel);
        return ProtoConverterStore
                .protoToModel(stub
                        .salvar(ProtoConverterStore.modelToProto(Servico))
                        .getServico());
    }

    public Servico buscar(Long id) {

        ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channel);
        ServicoProto proto = ServicoProto.newBuilder().setId(id).build();
        return ProtoConverterStore.protoToModel(stub.buscar(proto).getServico());
    }

    public Servico atualizar(Servico Servico) {
        ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channel);
        return ProtoConverterStore
                .protoToModel(stub
                        .atualizar(ProtoConverterStore.modelToProto(Servico)).getServico());
    }

    public void deletar(Long id) {

        ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channel);
        stub.deletar(ServicoProto.newBuilder().setId(id).build());
    }
}