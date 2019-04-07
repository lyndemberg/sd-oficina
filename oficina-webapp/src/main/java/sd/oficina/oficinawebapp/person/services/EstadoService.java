package sd.oficina.oficinawebapp.person.services;

import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.person.grpc.EstadoClient;
import sd.oficina.shared.model.person.Estado;

import java.util.List;

@Service
public class EstadoService {

    private EstadoClient estadoClient;

    public EstadoService() {
        this.estadoClient = new EstadoClient();
    }

    public Estado salvar (Estado estado) {
        return estadoClient.salvar(estado);
    }

    public Estado atualizar(Estado estado) {
        return estadoClient.atualizar(estado);
    }

    public void deletar(int idDoEstado) {
        estadoClient.deletar(idDoEstado);
    }

    public Estado buscar(int idDoEstado) {
        return estadoClient.buscar(idDoEstado);
    }

    public List<Estado> listar() {
        return estadoClient.listar();
    }
}
