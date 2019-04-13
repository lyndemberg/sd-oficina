package sd.oficina.oficinawebapp.person.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.identity.IdentityManager;
import sd.oficina.oficinawebapp.person.grpc.PersonClient;
import sd.oficina.oficinawebapp.rescue.RescueRepository;
import sd.oficina.shared.model.ActionEnum;
import sd.oficina.shared.model.EventRescue;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.shared.model.order.OrdemServico;
import sd.oficina.shared.model.person.Cliente;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private final PersonClient clienteClient;
    private final IdentityManager identityManager;
    private final RescueRepository rescueRepository;
    //CACHE
    private final HashOperations<String,Object, Cliente> hashOperations;
    private final RedisTemplate<String, Cliente> redisTemplate;

    public ClienteService(PersonClient clienteClient, IdentityManager identityManager, RescueRepository rescueRepository, @Qualifier("redisTemplatePerson") RedisTemplate<String, Cliente> redisTemplate) {
        this.clienteClient = clienteClient;
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public Cliente salvar (Cliente cliente) {
        Cliente persistido = null;
        long id = identityManager.gerarIdParaEntidade(Cliente.class.getSimpleName());
        cliente.setId(id);
        try {
            persistido = clienteClient.salvar(cliente);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Cliente.class.getSimpleName());
            eventRescue.setService(ServiceEnum.PERSON);
            eventRescue.setAction(ActionEnum.INSERT);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(cliente));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Cliente.class.getSimpleName(),cliente.getId(),cliente);
                persistido = cliente;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }

        return persistido;
    }

    public Cliente atualizar(Cliente cliente) {
        Cliente atualizado = null;
        try {
            atualizado = clienteClient.atualizar(cliente);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Cliente.class.getSimpleName());
            eventRescue.setService(ServiceEnum.PERSON);
            eventRescue.setAction(ActionEnum.UPDATE);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(cliente));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Cliente.class.getSimpleName(),cliente.getId(),cliente);
                atualizado = cliente;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return atualizado;
    }

    public void deletar(Long idDoCliente) {
        try {
            clienteClient.deletar(idDoCliente);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Cliente.class.getSimpleName());
            eventRescue.setService(ServiceEnum.PERSON);
            eventRescue.setAction(ActionEnum.DELETE);
            Cliente cliente = new Cliente();
            cliente.setId(idDoCliente);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(cliente));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.delete(Cliente.class.getSimpleName(),idDoCliente);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Cliente buscar(Long idDoCliente) {
        Cliente cliente = null;
        try {
            cliente = clienteClient.buscarCliente(idDoCliente);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            cliente = hashOperations.get(Cliente.class.getSimpleName(),idDoCliente);
        }
        return cliente;
    }

    public List<Cliente> listar() {
        List<Cliente> clientesList = new ArrayList<>();
        try {
            clientesList = clienteClient.listarClientes();
        } catch (FalhaGrpcException e) {
            clientesList =  hashOperations.values(Cliente.class.getSimpleName());
        }
        return clientesList;
    }
}
