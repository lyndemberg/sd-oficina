package sd.oficina.oficinawebapp.store.service;

import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.store.grpc.ServicoClient;
import sd.oficina.shared.model.store.Servico;

import java.util.List;

@Service
public class ServicoService {
    
    private ServicoClient grpc;

    public ServicoService() {
        this.grpc = new ServicoClient();
    }

    public Servico salvar(Servico Servico) {
        return grpc.salvar(Servico);
    }

    public Servico buscar(int id) {
        return grpc.buscar(id);
    }

    public void deletar(int id) {
        grpc.deletar(id);
    }

    public Servico atualizar(Servico Servico) {
        return grpc.atualizar(Servico);
    }

    public List<Servico> todos(){
        return grpc.todos();
    }
}
