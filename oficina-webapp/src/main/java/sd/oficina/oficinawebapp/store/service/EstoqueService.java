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
import sd.oficina.oficinawebapp.store.grpc.EstoqueClient;
import sd.oficina.shared.model.EventRescue;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.shared.model.customer.Fabricante;
import sd.oficina.shared.model.store.Estoque;

import java.util.List;

@Service
public class EstoqueService {

    private final HashOperations<String, Object, Estoque> hashOperations;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;
    private EstoqueClient grpc;
    private RedisTemplate<String,Estoque> redisTemplate;

    public EstoqueService(@Qualifier("redisTemplateStore") RedisTemplate<String, Estoque> template,
                          IdentityManager identityManager, RescueRepository rescueRepository,
                          EstoqueClient grpc) {
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
            persistido = grpc.salvar(estoque);
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
        return grpc.buscar(id);
    }

    public void deletar(int id) {
        grpc.deletar(id);
    }

    public Estoque atualizar(Estoque Estoque) {
        return grpc.atualizar(Estoque);
    }

    public List<Estoque> todos() {
        return grpc.buscarTodos();
    }
}
