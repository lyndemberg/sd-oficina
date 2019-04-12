package sd.oficina.oficinawebapp.person.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.identity.IdentityManager;
import sd.oficina.oficinawebapp.person.grpc.PersonClient;
import sd.oficina.oficinawebapp.rescue.RescueRepository;
import sd.oficina.shared.model.person.Cidade;

import java.util.ArrayList;
import java.util.List;

@Service
public class CidadeService {

    private final PersonClient personClient;
    private final RedisTemplate<String, Cidade> redisTemplate;
    private final HashOperations<String,Object, Cidade> hashOperations;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;

    public CidadeService(PersonClient personClient, @Qualifier("redisTemplateOrder") RedisTemplate<String, Cidade> redisTemplate, IdentityManager identityManager, RescueRepository rescueRepository) {
        this.personClient = personClient;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
    }

    public Cidade buscar(int idDaCidade) {
        Cidade cidade = null;
        try {
            cidade = personClient.buscarCidade(idDaCidade);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            cidade = hashOperations.get(Cidade.class.getSimpleName(),idDaCidade);
        }
        return cidade;
    }

    public List<Cidade> listar() {
        List<Cidade> cidades = new ArrayList<>();
        try {
            cidades = personClient.listarCidades();
        } catch (FalhaGrpcException e) {
            //buscando no cache
            cidades = hashOperations.values(Cidade.class.getSimpleName());
        }
        return cidades;
    }

    public List<Cidade> listarPorEstado(int idDoEstado) {
        List<Cidade> cidadesRecuperadas = new ArrayList<>();
        try {
            personClient.listarCidades().stream()
                    .filter(cidade -> cidade.getEstado().getId() == idDoEstado)
                    .forEach(c -> cidadesRecuperadas.add(c));
        } catch (FalhaGrpcException e) {
            //buscando no cache
            hashOperations.values(Cidade.class.getSimpleName())
                    .stream()
                    .filter(cidade -> cidade.getEstado().getId() == idDoEstado)
                    .forEach(c -> cidadesRecuperadas.add(c));
        }
        return cidadesRecuperadas;
    }
}
