package br.edu.ifpb.customer2.service;

import br.edu.ifpb.customer2.grpc.VeiculoClient;
import br.edu.ifpb.customer2.grpc.VeiculoClient;
import br.edu.ifpb.model.Veiculo;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
