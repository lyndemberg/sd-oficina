package sd.oficina.oficinawebapp.service;


import sd.oficina.oficinawebapp.grpc.AnoModeloClient;
import sd.oficina.shared.model.AnoModelo;
import org.springframework.stereotype.Service;

import java.util.List;

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
