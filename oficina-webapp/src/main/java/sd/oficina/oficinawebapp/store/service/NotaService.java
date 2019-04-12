package sd.oficina.oficinawebapp.store.service;

import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.store.grpc.NotaClient;
import sd.oficina.shared.model.store.Nota;

import java.util.List;

@Service
public class NotaService {
    
    private final NotaClient grpc;

    public NotaService() {
        this.grpc = new NotaClient();
    }

    public Nota salvar(Nota nota) {
        return grpc.salvar(nota);
    }

    public Nota buscar(int id) {
        return grpc.buscar(id);
    }

    public void deletar(int id) {
        grpc.deletar(id);
    }

    public Nota atualizar(Nota nota) {
        return grpc.atualizar(nota);
    }

    public List<Nota> todos(){
        return grpc.todas();
    }
}
