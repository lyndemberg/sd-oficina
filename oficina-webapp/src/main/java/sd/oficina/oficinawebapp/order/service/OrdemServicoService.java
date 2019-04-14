package sd.oficina.oficinawebapp.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sd.oficina.oficinawebapp.customer.service.VeiculoService;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.identity.IdentityManager;
import sd.oficina.oficinawebapp.order.grpc.OrderClient;
import sd.oficina.oficinawebapp.order.valueobject.OrdemServicoValue;
import sd.oficina.oficinawebapp.person.services.ClienteService;
import sd.oficina.oficinawebapp.rescue.RescueRepository;
import sd.oficina.oficinawebapp.store.service.ServicoService;
import sd.oficina.shared.model.ActionEnum;
import sd.oficina.shared.model.EventRescue;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.model.order.OrdemServico;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.shared.proto.order.OrdemProto;
import sd.oficina.shared.util.LocalDateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrdemServicoService {

    private final OrderClient clientOrderGrpc;
    private final ClienteService clienteService;
    private final VeiculoService veiculoService;
    private final ServicoService servicoService;
    //GERADOR DE ID
    private final IdentityManager identityManager;
    private final RescueRepository rescueRepository;
    //CACHE
    private final RedisTemplate<String, OrdemServico> redisTemplate;
    private final HashOperations<String, Object, OrdemServico> hashOperations;

    public OrdemServicoService(OrderClient clientOrderGrpc, ClienteService clienteService, VeiculoService veiculoService, ServicoService servicoService, IdentityManager identityManager, RescueRepository rescueRepository, @Qualifier("redisTemplateOrder") RedisTemplate<String, OrdemServico> redisTemplate) {
        this.clientOrderGrpc = clientOrderGrpc;
        this.veiculoService = veiculoService;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
        this.identityManager = identityManager;
        this.rescueRepository = rescueRepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void salvar(OrdemServicoValue value) {
        long id = identityManager.gerarIdParaEntidade(OrdemServico.class.getSimpleName());
        OrdemServico ordemServico = value.toEntity();
        ordemServico.setId(id);
        try {
            clientOrderGrpc.cadastrarNovaOrdem(ordemServico);
        } catch (FalhaGrpcException e) {
            //significa que falou na comunicação com o ORDER1 e ORDER2

            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(OrdemServico.class.getSimpleName());
            eventRescue.setService(ServiceEnum.ORDER);
            eventRescue.setAction(ActionEnum.INSERT);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(ordemServico));
                //salvando evento na tabela para o serviço executar no reinicio
                rescueRepository.save(eventRescue);
                //atualiza cache depois de inserir na tabela
                hashOperations.put(OrdemServico.class.getSimpleName(), ordemServico.getId(), ordemServico);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void realizarPagamento(OrdemServicoValue value) {
        value.setPago(true);
        OrdemServico ordemServico = value.toEntity();
        try {
            clientOrderGrpc.realizarPagamento(ordemServico);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(OrdemServico.class.getSimpleName());
            eventRescue.setService(ServiceEnum.ORDER);
            eventRescue.setAction(ActionEnum.UPDATE);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(ordemServico));
                rescueRepository.save(eventRescue);
                hashOperations.put(OrdemServico.class.getSimpleName(), ordemServico.getId(), ordemServico);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }

        }
    }

    public void concluirOrdem(OrdemServicoValue value) {
        value.setConcluida(true);
        OrdemServico ordemServico = value.toEntity();
        try {
            clientOrderGrpc.concluirOrdem(ordemServico);
        } catch (FalhaGrpcException e) {
            //CRIAR EVENTO NA TABELA
            EventRescue eventRescue = new EventRescue();
            eventRescue.setEntity(OrdemServico.class.getSimpleName());
            eventRescue.setService(ServiceEnum.ORDER);
            eventRescue.setAction(ActionEnum.UPDATE);
            ObjectMapper mapper = new ObjectMapper();
            try {
                eventRescue.setPayload(mapper.writeValueAsString(ordemServico));
                rescueRepository.save(eventRescue);
                hashOperations.put(OrdemServico.class.getSimpleName(), ordemServico.getId(), ordemServico);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public List<OrdemServicoValue> buscarOrdensDeServicoPorCliente(Long id) {
        List<OrdemServicoValue> ordemServicoValueList = new ArrayList<>();
        try {
            List<OrdemProto> protos = clientOrderGrpc.buscarOrdensPorCliente(id);
            protos.forEach((ordem) -> {
                //compõe Cliente
                Cliente cli = clienteService.buscar(ordem.getIdCliente());
                //compõe Veículo
                Veiculo veiculo = veiculoService.buscar(ordem.getIdVeiculo());
                List<Servico> servicosList = new ArrayList<>();
                ordem.getServicosList().forEach((servico) -> {
                    Servico s = servicoService.buscar(servico);
                    servicosList.add(s);
                });
                OrdemServicoValue value = new OrdemServicoValue();
                value.setId(ordem.getIdOrdem());
                value.setCliente(cli);
                value.setVeiculo(veiculo);
                value.setConcluida(ordem.getConcluido());
                value.setPago(ordem.getPago());
                value.setServicos(servicosList);
                value.setDataPagamento(LocalDateUtil.toLocalDate(ordem.getDataPagamento()));
                value.setDataRegistro(LocalDateUtil.toLocalDate(ordem.getDataRegistro()));
                ordemServicoValueList.add(value);
            });
        } catch (FalhaGrpcException e) {
            // Recupera do cache todas as OrdemServico baseando-se no id do Cliente
            List<OrdemServico> values = this.hashOperations.values(OrdemServico.class.getSimpleName());
            List<OrdemServico> collect = values.stream().filter(value -> id.equals(value.getIdCliente()))
                    .collect(Collectors.toList());
            collect.forEach(ordem -> {
                //compõe Cliente
                Cliente cli = clienteService.buscar(ordem.getIdCliente());
                //compõe Veículo
                Veiculo veiculo = veiculoService.buscar(ordem.getIdVeiculo());
                List<Servico> servicosList = new ArrayList<>();
                ordem.getServicos().forEach((servico) -> {
                    Servico s = servicoService.buscar(servico);
                    servicosList.add(s);
                });
                OrdemServicoValue value = new OrdemServicoValue();
                value.setId(ordem.getId());
                value.setCliente(cli);
                value.setVeiculo(veiculo);
                value.setConcluida(ordem.isConcluida());
                value.setPago(ordem.isPago());
                value.setServicos(servicosList);
                value.setDataPagamento(ordem.getDataPagamento());
                value.setDataRegistro(ordem.getDataRegistro());
                ordemServicoValueList.add(value);
            });
        }

        return ordemServicoValueList;
    }

    public List<OrdemServicoValue> listarTodos(){
        List<OrdemServicoValue> ordemServicoValueList = new ArrayList<>();
        try {
            List<OrdemProto> protos = clientOrderGrpc.listarOrdensServico();
            protos.forEach((ordem) -> {
                //compõe Cliente
                Cliente cli = clienteService.buscar(ordem.getIdCliente());
                //compõe Veículo
                Veiculo veiculo = veiculoService.buscar(ordem.getIdVeiculo());
                List<Servico> servicosList = new ArrayList<>();
                ordem.getServicosList().forEach((servico) -> {
                    Servico s = servicoService.buscar(servico);
                    servicosList.add(s);
                });
                OrdemServicoValue value = new OrdemServicoValue();
                value.setId(ordem.getIdOrdem());
                value.setCliente(cli);
                value.setVeiculo(veiculo);
                value.setConcluida(ordem.getConcluido());
                value.setPago(ordem.getPago());
                value.setServicos(servicosList);
                value.setDataPagamento(LocalDateUtil.toLocalDate(ordem.getDataPagamento()));
                value.setDataRegistro(LocalDateUtil.toLocalDate(ordem.getDataRegistro()));
                ordemServicoValueList.add(value);
            });
        } catch (FalhaGrpcException e) {
            // Recupera do cache todas as OrdemServico baseando-se no id do Cliente
            List<OrdemServico> values = this.hashOperations.values(OrdemServico.class.getSimpleName());
            values.forEach(ordem->{
                //compõe Cliente
                Cliente cli = clienteService.buscar(ordem.getIdCliente());
                //compõe Veículo
                Veiculo veiculo = veiculoService.buscar(ordem.getIdVeiculo());
                List<Servico> servicosList = new ArrayList<>();
                ordem.getServicos().forEach((servico) -> {
                    Servico s = servicoService.buscar(servico);
                    servicosList.add(s);
                });

                OrdemServicoValue value = new OrdemServicoValue();
                value.setId(ordem.getId());
                value.setCliente(cli);
                value.setVeiculo(veiculo);
                value.setConcluida(ordem.isConcluida());
                value.setPago(ordem.isPago());
                value.setServicos(servicosList);
                value.setDataPagamento(ordem.getDataPagamento());
                value.setDataRegistro(ordem.getDataRegistro());
                ordemServicoValueList.add(value);
            });

        }

        return ordemServicoValueList;
    }

}
