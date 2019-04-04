package sd.oficina.oficinawebapp.person.services;

import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.person.grpc.ClienteClient;
import sd.oficina.shared.model.person.Cliente;

import java.util.List;

@Service
public class ClienteService {

    private ClienteClient clienteClient;

    public ClienteService() {
        this.clienteClient = new ClienteClient();
    }

    public Cliente salvar (Cliente cliente) {
        return clienteClient.salvar(cliente);
    }

    public Cliente atualizar(Cliente cliente) {
        return clienteClient.atualizar(cliente);
    }

    public void deletar(int idDoCliente) {
        clienteClient.deletar(idDoCliente);
    }

    public Cliente buscar(int idDoCliente) {
        return clienteClient.buscar(idDoCliente);
    }

    public List<Cliente> listar() {
        return clienteClient.listar();
    }
}
