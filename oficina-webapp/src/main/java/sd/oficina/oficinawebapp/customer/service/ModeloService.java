package sd.oficina.oficinawebapp.customer.service;

import sd.oficina.oficinawebapp.customer.grpc.ModeloClient;
import sd.oficina.shared.model.customer.Modelo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {

    private ModeloClient grpc;

    public ModeloService() {
        this.grpc = new ModeloClient();
    }

    public Modelo salvar(Modelo modelo) {
        return grpc.salvar(modelo);
    }

    public Modelo buscar(int id) {
        return grpc.buscar(id);
    }

    public void deletar(int id) {
        grpc.deletar(id);
    }

    public Modelo atualizar(Modelo modelo) {
        return grpc.atualizar(modelo);
    }

    public List<Modelo> todos(){
        return grpc.todos();
    }
}
