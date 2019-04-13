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
import sd.oficina.shared.model.customer.Modelo;
import sd.oficina.shared.model.customer.Veiculo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VeiculoService {

    private final CustomerClient customerClient;
    private final RedisTemplate<String, Veiculo> redisTemplate;
    private final HashOperations<String,Object, Veiculo> hashOperations;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;

    public VeiculoService(CustomerClient customerClient, @Qualifier("redisTemplateCustomer") RedisTemplate<String, Veiculo> redisTemplate, IdentityManager identityManager, RescueRepository rescueRepository) {
        this.customerClient = customerClient;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
    }


    public Veiculo salvar(Veiculo veiculo) {
        Veiculo persistido = null;
        long id = identityManager.gerarIdParaEntidade(Modelo.class.getSimpleName());
        veiculo.setId(id);
        try {
            persistido = customerClient.salvarVeiculo(veiculo);
        } catch (FalhaGrpcException ex) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Veiculo.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction("SAVE");
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(veiculo));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Veiculo.class.getSimpleName(),veiculo.getId(),veiculo);
                persistido = veiculo;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return persistido;
    }

    public Veiculo buscar(Long id) {
        Veiculo veiculo = null;
        try {
            veiculo = customerClient.buscarVeiculo(id);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            veiculo = hashOperations.get(Veiculo.class.getSimpleName(),id);
        }
        return veiculo;
    }

    public void deletar(Long id) {
        try {
            customerClient.deletarVeiculo(id);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Veiculo.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction("DELETE");
            Veiculo veiculo = new Veiculo();
            veiculo.setId(id);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(veiculo));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.delete(Veiculo.class.getSimpleName(),id);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Veiculo atualizar(Veiculo veiculo) {
        Veiculo atualizado = null;
        try {
            atualizado = customerClient.atualizarVeiculo(veiculo);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Veiculo.class.getSimpleName());
            eventRescue.setService(ServiceEnum.CUSTOMER);
            eventRescue.setAction("UPDATE");
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(veiculo));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Veiculo.class.getSimpleName(),veiculo.getId(),veiculo);
                atualizado = veiculo;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return atualizado;
    }

    public List<Veiculo> todos(){
        List<Veiculo> veiculoList = new ArrayList<>();
        try {
            veiculoList = customerClient.todosVeiculos();
        } catch (FalhaGrpcException e) {
            veiculoList =  hashOperations.values(Veiculo.class.getSimpleName());
        }
        return veiculoList;
    }
}
