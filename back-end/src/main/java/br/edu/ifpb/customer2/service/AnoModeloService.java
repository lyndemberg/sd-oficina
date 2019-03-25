package br.edu.ifpb.customer2.service;


import br.edu.ifpb.customer2.grpc.AnoModeloClient;
import br.edu.ifpb.model.AnoModelo;
import br.edu.ifpb.model.Modelo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnoModeloService {

    private AnoModeloClient grpc;

    public AnoModeloService() {
        this.grpc = new AnoModeloClient();
    }

    public AnoModelo salvar(AnoModelo anoModelo) {
        return grpc.salvar(anoModelo);
    }

    public AnoModelo buscar(int id) {
        return grpc.buscar(id);
    }

    public void deletar(int id) {
        grpc.deletar(id);
    }

    public AnoModelo atualizar(AnoModelo anoModelo) {
        return grpc.atualizar(anoModelo);
    }

    public List<AnoModelo> todos(){
        return grpc.todos();
    }
}
