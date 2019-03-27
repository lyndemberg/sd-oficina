package sd.oficina.oficinawebapp.service;

import sd.oficina.oficinawebapp.grpc.FabricanteClient;
import sd.oficina.shared.model.Fabricante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FabricanteService {


    private FabricanteClient grpc;

    public FabricanteService() {
        this.grpc = new FabricanteClient();
    }

    public Fabricante salvar(Fabricante fabricante) {
        return grpc.salvar(fabricante);
    }

    public Fabricante buscar(int id) {
        return grpc.buscar(id);
    }

    public void deletar(int id) {
        grpc.deletar(id);
    }

    public Fabricante atualizar(Fabricante fabricante) {
        return grpc.atualizar(fabricante);
    }

    public List<Fabricante> todos(){
        return grpc.todos();
    }
}
