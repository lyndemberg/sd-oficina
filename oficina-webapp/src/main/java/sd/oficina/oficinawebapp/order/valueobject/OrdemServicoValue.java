package sd.oficina.oficinawebapp.order.valueobject;

import lombok.Data;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.model.store.Servico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrdemServicoValue {
    private Long id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataRegistro;
    private LocalDate dataPagamento;
    private boolean pago;
    private boolean concluida;
    private List<Servico> servicos;

    public OrdemServicoValue(){
        this.servicos = new ArrayList<>();
    }

    public OrdemServicoValue(Long id, Cliente cliente, Veiculo veiculo, LocalDate dataRegistro, LocalDate dataPagamento, boolean pago, boolean concluida, List<Servico> servicos) {
        this.servicos = new ArrayList<>();
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataRegistro = dataRegistro;
        this.dataPagamento = dataPagamento;
        this.pago = pago;
        this.concluida = concluida;
        this.servicos = servicos;
    }

    public void addServico(Servico servicoValue){
        this.servicos.add(servicoValue);
    }

    public void removerServico(Servico servicoValue){
        this.servicos.remove(servicoValue);
    }

    public List<Long> getIdServicos(){
        List<Long> servicosId = new ArrayList<>();
        for(Servico s : this.servicos){
            servicosId.add(s.getId());
        }

        return servicosId;
    }





}
