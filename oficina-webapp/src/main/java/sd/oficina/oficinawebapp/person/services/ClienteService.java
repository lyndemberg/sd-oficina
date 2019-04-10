package sd.oficina.oficinawebapp.person.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.person.grpc.ClienteClient;
import sd.oficina.shared.model.person.Cliente;

import java.util.List;

@Service
public class ClienteService {

    private ClienteClient clienteClient;

    //CACHE
    private final HashOperations<String,Object, Cliente> hashOperations;
    private final RedisTemplate<String, Cliente> redisTemplate;

    public ClienteService(@Qualifier("redisTemplatePerson")  RedisTemplate<String, Cliente> redisTemplate) {
        this.clienteClient = new ClienteClient();
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public Cliente salvar (Cliente cliente) {
        //return clienteClient.salvar(cliente);

        //SALVANDO NO CACHE
        hashOperations.put(Cliente.class.getSimpleName(),cliente.getId(),cliente);
        return null;
    }

    public Cliente atualizar(Cliente cliente) {
        return clienteClient.atualizar(cliente);
    }

    public void deletar(Long idDoCliente) {
        clienteClient.deletar(idDoCliente);
    }

    public Cliente buscar(Long idDoCliente) {
        return clienteClient.buscar(idDoCliente);
    }

    public List<Cliente> listar() {
        //RECUPERANDO NO CACHE
         return hashOperations.values(Cliente.class.getSimpleName());

        //return clienteClient.listar();
    }
}
