package br.edu.ifpb.customer2.service;

import br.edu.ifpb.customer2.grpc.ModeloClient;
import br.edu.ifpb.model.Modelo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
