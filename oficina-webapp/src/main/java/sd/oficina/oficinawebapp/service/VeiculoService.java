package sd.oficina.oficinawebapp.service;

import sd.oficina.oficinawebapp.grpc.VeiculoClient;
import sd.oficina.shared.model.customer.Veiculo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    private VeiculoClient grpc;

    public VeiculoService() {
        this.grpc = new VeiculoClient();
    }

    public Veiculo salvar(Veiculo veiculo) {
        return grpc.salvar(veiculo);
    }

    public Veiculo buscar(int id) {
        return grpc.buscar(id);
    }

    public void deletar(int id) {
        grpc.deletar(id);
    }

    public Veiculo atualizar(Veiculo veiculo) {
        return grpc.atualizar(veiculo);
    }

    public List<Veiculo> todos(){
        return grpc.todos();
    }
}
