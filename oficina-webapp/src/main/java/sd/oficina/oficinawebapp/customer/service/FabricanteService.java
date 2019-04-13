package sd.oficina.oficinawebapp.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.oficinawebapp.customer.grpc.CustomerClient;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.identity.IdentityManager;
import sd.oficina.oficinawebapp.rescue.RescueRepository;
import sd.oficina.shared.model.ActionEnum;
import sd.oficina.shared.model.EventRescue;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.shared.model.customer.Fabricante;
import org.springframework.stereotype.Service;
import sd.oficina.shared.model.person.Cliente;

import java.util.ArrayList;
import java.util.List;

@Service
public class FabricanteService {


    private final CustomerClient customerClient;
    private final RedisTemplate<String, Fabricante> redisTemplate;
    private final HashOperations<String,Object, Fabricante> hashOperations;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;

    public FabricanteService(CustomerClient customerClient, @Qualifier("redisTemplateCustomer") RedisTemplate<String, Fabricante> redisTemplate, IdentityManager identityManager, RescueRepository rescueRepository) {
        this.customerClient = customerClient;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
    }


    public Fabricante salvar(Fabricante fabricante) {
        Fabricante persistido = null;
        long id = identityManager.gerarIdParaEntidade(Fabricante.class.getSimpleName());
        fabricante.setId(id);
        try {
            persistido = customerClient.salvarFabricante(fabricante);
        } catch (FalhaGrpcException ex) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Fabricante.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction(ActionEnum.INSERT);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(fabricante));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Fabricante.class.getSimpleName(),fabricante.getId(),fabricante);
                persistido = fabricante;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return persistido;
    }

    public Fabricante buscar(int id) {
        Fabricante fabricante = null;
        try {
            fabricante = customerClient.buscarFabricante(id);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            fabricante = hashOperations.get(Fabricante.class.getSimpleName(),id);
        }
        return fabricante;
    }

    public void deletar(int id) {
        try {
            customerClient.deletarFabricante(id);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Fabricante.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction(ActionEnum.DELETE);
            Fabricante fabricante = new Fabricante();
            fabricante.setId(id);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(fabricante));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.delete(Fabricante.class.getSimpleName(),id);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }

    }

    public Fabricante atualizar(Fabricante fabricante) {
        Fabricante atualizado = null;
        try {
            atualizado = customerClient.atualizarFabricante(fabricante);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Fabricante.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction(ActionEnum.UPDATE);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(fabricante));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Fabricante.class.getSimpleName(),fabricante.getId(),fabricante);
                atualizado = fabricante;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return atualizado;

    }

    public List<Fabricante> todos(){
        List<Fabricante> fabricanteList = new ArrayList<>();
        try {
            fabricanteList = customerClient.todosFabricantes();
        } catch (FalhaGrpcException e) {
            fabricanteList =  hashOperations.values(Fabricante.class.getSimpleName());
        }
        return fabricanteList;
    }
}
