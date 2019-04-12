package sd.oficina.oficinawebapp.person.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.identity.IdentityManager;
import sd.oficina.oficinawebapp.person.grpc.PersonClient;
import sd.oficina.oficinawebapp.rescue.RescueRepository;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.model.person.Estado;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstadoService {

    private PersonClient estadoClient;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;
    //CACHE
    private final HashOperations<String,Object, Estado> hashOperations;
    private final RedisTemplate<String, Estado> redisTemplate;

    public EstadoService(PersonClient estadoClient, IdentityManager identityManager, RescueRepository rescueRepository,  @Qualifier("redisTemplatePerson") RedisTemplate<String, Estado> redisTemplate) {
        this.estadoClient = estadoClient;
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public Estado buscar(int idDoEstado) {
        Estado estado = null;
        try {
            estado = estadoClient.buscarEstado(idDoEstado);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            estado = hashOperations.get(Estado.class.getSimpleName(),idDoEstado);
        }

        return estado;

    }

    public List<Estado> listar() {
        List<Estado> estadoList = new ArrayList<>();
        try {
            estadoList = estadoClient.listarEstados();
        } catch (FalhaGrpcException e) {
            //buscando no cache
            estadoList = hashOperations.values(Estado.class.getSimpleName());
        }

        return estadoList;
    }
}
