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
import sd.oficina.shared.model.customer.AnoModelo;
import org.springframework.stereotype.Service;
import sd.oficina.shared.model.person.Cliente;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnoModeloService {

    private final CustomerClient customerClient;
    private final RedisTemplate<String, AnoModelo> redisTemplate;
    private final HashOperations<String,Object, AnoModelo> hashOperations;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;

    public AnoModeloService(CustomerClient customerClient, @Qualifier("redisTemplateCustomer") RedisTemplate<String, AnoModelo> redisTemplate, IdentityManager identityManager, RescueRepository rescueRepository) {
        this.customerClient = customerClient;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
    }

    public AnoModelo salvar(AnoModelo anoModelo) {
        AnoModelo persistido = null;
        long id = identityManager.gerarIdParaEntidade(Cliente.class.getSimpleName());
        anoModelo.setId(id);
        try {
            persistido = customerClient.salvarAnoModelo(anoModelo);
        } catch (FalhaGrpcException ex) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(AnoModelo.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction(ActionEnum.INSERT);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(anoModelo));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(AnoModelo.class.getSimpleName(),anoModelo.getId(),anoModelo);
                persistido = anoModelo;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return persistido;
    }

    public AnoModelo buscar(int idAnoModelo) {
        AnoModelo anoModelo = null;
        try {
            anoModelo = customerClient.buscarAnoModelo(idAnoModelo);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            anoModelo = hashOperations.get(AnoModelo.class.getSimpleName(),idAnoModelo);
        }
        return anoModelo;
    }

    public void deletar(int id) {
        try {
            customerClient.deletarAnoModelo(id);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(AnoModelo.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction(ActionEnum.DELETE);
            AnoModelo anoModelo = new AnoModelo();
            anoModelo.setId(id);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(anoModelo));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.delete(AnoModelo.class.getSimpleName(),id);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public AnoModelo atualizar(AnoModelo anoModelo) {
        AnoModelo atualizado = null;
        try {
            atualizado = customerClient.atualizarAnoModelo(anoModelo);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(AnoModelo.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction(ActionEnum.UPDATE);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(anoModelo));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(AnoModelo.class.getSimpleName(),anoModelo.getId(),anoModelo);
                atualizado = anoModelo;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return atualizado;

    }

    public List<AnoModelo> todos(){
        List<AnoModelo> anoModeloList = new ArrayList<>();
        try {
            anoModeloList = customerClient.listarAnoModelo();
        } catch (FalhaGrpcException e) {
            anoModeloList =  hashOperations.values(AnoModelo.class.getSimpleName());
        }
        return anoModeloList;
    }
}
