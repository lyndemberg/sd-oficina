package sd.oficina.oficinawebapp.store.service;

import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.store.grpc.EstoqueClient;
import sd.oficina.shared.model.store.Estoque;

import java.util.List;

@Service
public class EstoqueService {

    private EstoqueClient grpc;

    public EstoqueService() {
        this.grpc = new EstoqueClient();
    }

    public Estoque salvar(Estoque Estoque) {
        return grpc.salvar(Estoque);
    }

    public Estoque buscar(int id) {
        return grpc.buscar(id);
    }

    public void deletar(int id) {
        grpc.deletar(id);
    }

    public Estoque atualizar(Estoque Estoque) {
        return grpc.atualizar(Estoque);
    }

    public List<Estoque> todos(){
        return grpc.buscarTodos();
    }
}
