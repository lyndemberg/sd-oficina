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
import sd.oficina.shared.model.store.Nota;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaService {

    private final HashOperations<String, Object, Nota> hashOperations;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;
    private final StoreClient grpc;
    private final RedisTemplate<String,Nota> redisTemplate;

    public NotaService(IdentityManager identityManager, RescueRepository rescueRepository, StoreClient grpc, @Qualifier("redisTemplateStore") RedisTemplate<String, Nota> redisTemplate) {
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
        this.grpc = grpc;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public Nota salvar(Nota nota) {
        Nota persistido = null;
        long id = identityManager.gerarIdParaEntidade(Nota.class.getSimpleName());
        nota.setId(id);
        try {
            persistido = grpc.salvarNota(nota);
        } catch (FalhaGrpcException ex) {
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Nota.class.getSimpleName());
            eventRescue.setService(ServiceEnum.STORE);
            eventRescue.setAction(ActionEnum.INSERT);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(nota));
                rescueRepository.save(eventRescue);
                hashOperations.put(Nota.class.getSimpleName(), nota.getId(), nota);
                persistido = nota;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return persistido;
    }

    public Nota buscar(int id) {
        Nota nota = null;
        try {
            nota = grpc.buscarNota(id);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            nota = hashOperations.get(Nota.class.getSimpleName(),id);
        }
        return nota;
    }

    public void deletar(int id) {
        try {
            grpc.deletarNota(id);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Nota.class.getSimpleName());
            eventRescue.setService(ServiceEnum.STORE);
            eventRescue.setAction(ActionEnum.DELETE);
            Nota nota = new Nota();
            nota.setId(id);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(nota));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.delete(Nota.class.getSimpleName(),id);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Nota atualizar(Nota nota) {
        Nota atualizado = null;
        try {
            atualizado = grpc.atualizarNota(nota);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Nota.class.getSimpleName());
            eventRescue.setService(ServiceEnum.STORE);
            eventRescue.setAction(ActionEnum.UPDATE);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(nota));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Nota.class.getSimpleName(),nota.getId(),nota);
                atualizado = nota;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return atualizado;
    }

    public List<Nota> todos(){
        List<Nota> notaList = new ArrayList<>();
        try {
            notaList = grpc.todasNotas();
        } catch (FalhaGrpcException e) {
            notaList =  hashOperations.values(Nota.class.getSimpleName());
        }
        return notaList;
    }
}
