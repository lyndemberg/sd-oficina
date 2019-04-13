package sd.oficina.oficinawebapp.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.identity.IdentityManager;
import sd.oficina.oficinawebapp.rescue.RescueRepository;
import sd.oficina.oficinawebapp.store.grpc.StoreClient;
import sd.oficina.shared.model.ActionEnum;
import sd.oficina.shared.model.EventRescue;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Servico;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicoService {

    private final HashOperations<String, Object, Servico> hashOperations;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;
    private final StoreClient grpc;
    private final RedisTemplate<String,Servico> redisTemplate;

    public ServicoService(IdentityManager identityManager, RescueRepository rescueRepository, StoreClient grpc, @Qualifier("redisTemplateStore") RedisTemplate<String, Servico> redisTemplate) {
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
        this.grpc = grpc;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public Servico salvar(Servico servico) {
        Servico persistido = null;
        long id = identityManager.gerarIdParaEntidade(Servico.class.getSimpleName());
        servico.setId(id);
        try {
            persistido = grpc.salvarServico(servico);
        } catch (FalhaGrpcException ex) {
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Servico.class.getSimpleName());
            eventRescue.setService(ServiceEnum.STORE);
            eventRescue.setAction(ActionEnum.INSERT);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(servico));
                rescueRepository.save(eventRescue);
                hashOperations.put(Servico.class.getSimpleName(), servico.getId(), servico);
                persistido = servico;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return persistido;
    }

    public Servico buscar(Long id) {
        Servico servico = null;
        try {
            servico = grpc.buscarServico(id);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            servico = hashOperations.get(Servico.class.getSimpleName(),id);
        }
        return servico;
    }

    public void deletar(Long id) {
        try {
            grpc.deletarServico(id);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Servico.class.getSimpleName());
            eventRescue.setService(ServiceEnum.STORE);
            eventRescue.setAction(ActionEnum.DELETE);
            Servico servico = new Servico();
            servico.setId(id);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(servico));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.delete(Servico.class.getSimpleName(),id);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Servico atualizar(Servico servico) {
        Servico atualizado = null;
        try {
            atualizado = grpc.atualizarServico(servico);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Servico.class.getSimpleName());
            eventRescue.setService(ServiceEnum.STORE);
            eventRescue.setAction(ActionEnum.UPDATE);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(servico));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Servico.class.getSimpleName(),servico.getId(),servico);
                atualizado = servico;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return atualizado;
    }

    public List<Servico> todos(){
        List<Servico> servicoList = new ArrayList<>();
        try {
            servicoList = grpc.todosServicos();
        } catch (FalhaGrpcException e) {
            servicoList =  hashOperations.values(Servico.class.getSimpleName());
        }
        return servicoList;
    }
}
