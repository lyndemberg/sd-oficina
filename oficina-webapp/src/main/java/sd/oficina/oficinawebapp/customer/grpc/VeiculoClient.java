package sd.oficina.oficinawebapp.customer.grpc;

import sd.oficina.shared.model.customer.Veiculo;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.shared.proto.customer.VeiculoProto;
import sd.oficina.shared.proto.customer.VeiculoServiceGrpc;

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
        lista.forEach(v-> veiculos.add(ProtoConverterCustomer.protoToModel(v)));
        return  veiculos;
        
    }

    public Veiculo salvar(Veiculo veiculo) {

        VeiculoProto grpc = ProtoConverterCustomer.modelToProto(veiculo);
        VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channel);
        return ProtoConverterCustomer
                .protoToModel(stub
                        .salvar(ProtoConverterCustomer.modelToProto(veiculo))
                        .getVeiculo());
    }

    public Veiculo buscar(Long id) {

        VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channel);
        VeiculoProto proto = VeiculoProto.newBuilder().setId(id).build();
        return ProtoConverterCustomer.protoToModel(stub.buscar(proto).getVeiculo());
    }

    public Veiculo atualizar(Veiculo veiculo) {
        VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channel);
        return ProtoConverterCustomer
                .protoToModel(stub
                        .atualizar(ProtoConverterCustomer.modelToProto(veiculo)).getVeiculo());
    }

    public void deletar(Long id) {

        VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channel);
        stub.deletar(VeiculoProto.newBuilder().setId(id).build());
    }
}
