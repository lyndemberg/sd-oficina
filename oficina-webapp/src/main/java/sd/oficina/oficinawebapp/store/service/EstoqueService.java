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
import sd.oficina.shared.model.EventRescue;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.shared.model.store.Estoque;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueService {

    private final HashOperations<String, Object, Estoque> hashOperations;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;
    private final StoreClient grpc;
    private final RedisTemplate<String,Estoque> redisTemplate;

    public EstoqueService(@Qualifier("redisTemplateStore") RedisTemplate<String, Estoque> template,
                          IdentityManager identityManager, RescueRepository rescueRepository, StoreClient grpc) {
        this.redisTemplate = template;
        this.hashOperations = template.opsForHash();
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
        this.grpc = grpc;
    }

    public Estoque salvar(Estoque estoque) {
        Estoque persistido = null;
        long id = identityManager.gerarIdParaEntidade(Estoque.class.getSimpleName());
        estoque.setIdPeca(id);
        try {
            persistido = grpc.salvarEstoque(estoque);
        } catch (FalhaGrpcException ex) {
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Estoque.class.getSimpleName());
            eventRescue.setService(ServiceEnum.STORE);
            eventRescue.setAction("SAVE");
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(estoque));
                rescueRepository.save(eventRescue);
                hashOperations.put(Estoque.class.getSimpleName(), estoque.getIdPeca(), estoque);
                persistido = estoque;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return persistido;
    }

    public Estoque buscar(int id) {
        Estoque estoque = null;
        try {
            estoque = grpc.buscarEstoque(id);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            estoque = hashOperations.get(Estoque.class.getSimpleName(),id);
        }
        return estoque;
    }

    public void deletar(int id) {
        try {
            grpc.deletarEstoque(id);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Estoque.class.getSimpleName());
            eventRescue.setService(ServiceEnum.STORE);
            eventRescue.setAction("DELETE");
            Estoque estoque = new Estoque();
            estoque.setIdPeca(id);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(estoque));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.delete(Estoque.class.getSimpleName(),id);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Estoque atualizar(Estoque estoque) {
        Estoque atualizado = null;
        try {
            atualizado = grpc.atualizarEstoque(estoque);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Estoque.class.getSimpleName());
            eventRescue.setService(ServiceEnum.STORE);
            eventRescue.setAction("UPDATE");
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(estoque));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Estoque.class.getSimpleName(),estoque.getIdPeca(),estoque);
                atualizado = estoque;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return atualizado;
    }

    public List<Estoque> todos() {
        List<Estoque> clientesList = new ArrayList<>();
        try {
            clientesList = grpc.buscarTodosEstoque();
        } catch (FalhaGrpcException e) {
            clientesList =  hashOperations.values(Estoque.class.getSimpleName());
        }
        return clientesList;
    }
}
