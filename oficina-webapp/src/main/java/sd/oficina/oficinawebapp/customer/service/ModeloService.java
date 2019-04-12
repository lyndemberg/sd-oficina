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
import sd.oficina.shared.model.EventRescue;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.shared.model.customer.AnoModelo;
import sd.oficina.shared.model.customer.Fabricante;
import sd.oficina.shared.model.customer.Modelo;
import org.springframework.stereotype.Service;
import sd.oficina.shared.model.person.Cliente;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModeloService {

    private final CustomerClient customerClient;
    private final RedisTemplate<String, Modelo> redisTemplate;
    private final HashOperations<String,Object, Modelo> hashOperations;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;

    public ModeloService(CustomerClient customerClient, @Qualifier("redisTemplateCustomer") RedisTemplate<String, Modelo> redisTemplate, IdentityManager identityManager, RescueRepository rescueRepository) {
        this.customerClient = customerClient;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
    }

    public Modelo salvar(Modelo modelo) {
        Modelo persistido = null;
        long id = identityManager.gerarIdParaEntidade(Modelo.class.getSimpleName());
        modelo.setId(id);
        try {
            persistido = customerClient.salvarModelo(modelo);
        } catch (FalhaGrpcException ex) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Modelo.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction("SAVE");
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(modelo));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Modelo.class.getSimpleName(),modelo.getId(),modelo);
                persistido = modelo;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return persistido;
    }

    public Modelo buscar(int id) {
        Modelo modelo = null;
        try {
            modelo = customerClient.buscarModelo(id);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            modelo = hashOperations.get(Modelo.class.getSimpleName(),id);
        }
        return modelo;
    }

    public void deletar(int id) {
        try {
            customerClient.deletarModelo(id);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Modelo.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction("DELETE");
            Modelo modelo = new Modelo();
            modelo.setId(id);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(modelo));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.delete(Modelo.class.getSimpleName(),id);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Modelo atualizar(Modelo modelo) {
        Modelo atualizado = null;
        try {
            atualizado = customerClient.atualizarModelo(modelo);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Modelo.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction("UPDATE");
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(modelo));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Modelo.class.getSimpleName(),modelo.getId(),modelo);
                atualizado = modelo;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return atualizado;
    }

    public List<Modelo> todos(){
        List<Modelo> modeloList = new ArrayList<>();
        try {
            modeloList = customerClient.todosModelos();
        } catch (FalhaGrpcException e) {
            modeloList =  hashOperations.values(Modelo.class.getSimpleName());
        }
        return modeloList;
    }
}
