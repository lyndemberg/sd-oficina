package sd.oficina.oficinawebapp.grpc;

import sd.oficina.shared.model.Veiculo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.proto.VeiculoProto;
import sd.oficina.shared.proto.VeiculoServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class VeiculoClient {

    private Veiculo retorno;
    private ManagedChannel channel;

    public VeiculoClient() {
        retorno = new Veiculo();
        channel = ManagedChannelBuilder
                .forAddress("localhost", 2222)
                .usePlaintext()
                .build();
    }

    public List<Veiculo> todos() {

        List<VeiculoProto> lista = VeiculoServiceGrpc
                .newBlockingStub(channel)
                .buscarTodos(Empty.newBuilder().build())
                .getVeiculosList();

        List<Veiculo> veiculos = new ArrayList<>();
        lista.forEach(v-> veiculos.add(ProtoConverter.protoToModel(v)));
        return  veiculos;
        
    }

    public Veiculo salvar(Veiculo veiculo) {

        VeiculoProto grpc = ProtoConverter.modelToProto(veiculo);
        VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channel);
        return ProtoConverter
                .protoToModel(stub
                        .salvar(ProtoConverter.modelToProto(veiculo))
                        .getVeiculo());
    }

    public Veiculo buscar(int id) {

        VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channel);
        VeiculoProto proto = VeiculoProto.newBuilder().setId(id).build();
        return ProtoConverter.protoToModel(stub.buscar(proto).getVeiculo());
    }

    public Veiculo atualizar(Veiculo veiculo) {
        VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channel);
        return ProtoConverter
                .protoToModel(stub
                        .atualizar(ProtoConverter.modelToProto(veiculo)).getVeiculo());
    }

    public void deletar(int id) {

        VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channel);
        stub.deletar(VeiculoProto.newBuilder().setId(id).build());
    }
}
