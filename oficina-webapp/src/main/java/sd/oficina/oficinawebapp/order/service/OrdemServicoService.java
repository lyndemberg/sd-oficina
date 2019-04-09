package sd.oficina.oficinawebapp.order.service;

import org.springframework.stereotype.Service;

import sd.oficina.oficinawebapp.customer.service.VeiculoService;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.oficinawebapp.order.grpc.IOrderClient;
import sd.oficina.oficinawebapp.order.grpc.OrderClientImpl;
import sd.oficina.oficinawebapp.order.valueobject.OrdemServicoValue;
import sd.oficina.oficinawebapp.person.services.ClienteService;
import sd.oficina.oficinawebapp.store.service.ServicoService;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.shared.proto.order.OrdemProto;
import sd.oficina.shared.util.LocalDateUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdemServicoService {

    private final IOrderClient clientOrderGrpc;
    private final ClienteService clienteService;
    private final VeiculoService veiculoService;
    private final ServicoService servicoService;

    public OrdemServicoService(ClienteService clienteService, VeiculoService veiculoService, ServicoService servicoService) {
        this.veiculoService = veiculoService;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
        clientOrderGrpc = new OrderClientImpl();
    }

    public void salvar(OrdemServicoValue value){
        try {
            clientOrderGrpc.cadastrarNovaOrdem(value);
        } catch (FalhaGrpcException e) {
            e.printStackTrace();
        }
    }

    public void realizarPagamento(OrdemServicoValue value){
        try {
            clientOrderGrpc.realizarPagamento(value);
        } catch (FalhaGrpcException e) {
            e.printStackTrace();
        }
    }

    public void concluirOrdem(OrdemServicoValue value){
        try {
            clientOrderGrpc.concluirOrdem(value);
        } catch (FalhaGrpcException e) {
            e.printStackTrace();
        }
    }

    public List<OrdemServicoValue> buscarOrdensDeServicoPorCliente(Cliente cliente){
        List<OrdemServicoValue> ordemServicoValueList = new ArrayList<>();
        try {
            List<OrdemProto> protos = clientOrderGrpc.buscarOrdensPorCliente(cliente);
            protos.stream().forEach((ordem)->{
                //compõe Cliente
                Cliente cli = clienteService.buscar(ordem.getIdCliente());
                //compõe Veículo
                Veiculo veiculo = veiculoService.buscar(ordem.getIdVeiculo());
                List<Servico> servicosList = new ArrayList<>();
                ordem.getServicosList().stream().forEach((servico)->{
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
            e.printStackTrace();
        }
        return ordemServicoValueList;
    }

}
