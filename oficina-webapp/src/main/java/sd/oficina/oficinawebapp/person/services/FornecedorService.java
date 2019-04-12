package sd.oficina.oficinawebapp.person.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.identity.IdentityManager;
import sd.oficina.oficinawebapp.person.grpc.PersonClient;
import sd.oficina.oficinawebapp.rescue.RescueRepository;
import sd.oficina.shared.model.EventRescue;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.model.person.Estado;
import sd.oficina.shared.model.person.Fornecedor;

import java.util.ArrayList;
import java.util.List;

@Service
public class FornecedorService {

    private final PersonClient fornecedorClient;
    private final IdentityManager identityManager;
    private final sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;
    //CACHE
    private final HashOperations<String,Object, Fornecedor> hashOperations;
    private final RedisTemplate<String, Fornecedor> redisTemplate;

    public FornecedorService(PersonClient fornecedorClient, IdentityManager identityManager, RescueRepository rescueRepository, @Qualifier("redisTemplatePerson") RedisTemplate<String, Fornecedor> redisTemplate) {
        this.fornecedorClient = fornecedorClient;
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public Fornecedor salvar (Fornecedor fornecedor) {
        Fornecedor persistido = null;
        long id = identityManager.gerarIdParaEntidade(Fornecedor.class.getSimpleName());
        fornecedor.setId(id);
        try {
            persistido = fornecedorClient.salvar(fornecedor);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Cliente.class.getSimpleName());
            eventRescue.setService(ServiceEnum.PERSON);
            eventRescue.setAction("SAVE");
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(fornecedor));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Fornecedor.class.getSimpleName(),fornecedor.getId(),fornecedor);
                persistido = fornecedor;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }

        return persistido;
    }

    public Fornecedor atualizar(Fornecedor fornecedor) {
        Fornecedor atualizado = null;
        try {
            atualizado = fornecedorClient.atualizar(fornecedor);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Cliente.class.getSimpleName());
            eventRescue.setService(ServiceEnum.PERSON);
            eventRescue.setAction("UPDATE");
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(fornecedor));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(Fornecedor.class.getSimpleName(),fornecedor.getId(),fornecedor);
                atualizado = fornecedor;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return atualizado;

    }

    public void deletar(int idDoFornecedor) {
        try {
            fornecedorClient.deletar(idDoFornecedor);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(Cliente.class.getSimpleName());
            eventRescue.setService(ServiceEnum.PERSON);
            eventRescue.setAction("DELETE");
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(idDoFornecedor);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(fornecedor));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.delete(Fornecedor.class.getSimpleName(), idDoFornecedor);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Fornecedor buscar(int idDoFornecedor) {
        Fornecedor fornecedor = null;
        try {
            fornecedor = fornecedorClient.buscar(idDoFornecedor);
        } catch (FalhaGrpcException e) {
            //buscando no cache
            fornecedor = hashOperations.get(Fornecedor.class.getSimpleName(),idDoFornecedor);
        }
        return fornecedor;
    }

    public List<Fornecedor> listar() {
        List<Fornecedor> fornecedorList = new ArrayList<>();
        try {
            fornecedorList = fornecedorClient.listar();
        } catch (FalhaGrpcException e) {
            fornecedorList =  hashOperations.values(Fornecedor.class.getSimpleName());
        }
        return fornecedorList;
    }
}
