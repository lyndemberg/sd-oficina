package br.edu.ifpb.customer2.service;

import br.edu.ifpb.customer2.grpc.FabricanteClient;
import br.edu.ifpb.model.Fabricante;
import org.springframework.stereotype.Service;

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
}
