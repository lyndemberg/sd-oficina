package br.edu.ifpb.customer2.service;

import br.edu.ifpb.customer2.grpc.FabricanteGrpc;
import br.edu.ifpb.model.Fabricante;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FabricanteService {


    private FabricanteGrpc grpc;

    public FabricanteService(){
        this.grpc = new FabricanteGrpc();
    }

    public Fabricante salvar(Fabricante fabricante){
        return grpc.salvar(fabricante);
    }
}
