package sd.oficina.oficinawebapp.order.valueobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.model.order.OrdemServico;
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

    @JsonIgnore
    public List<Long> getIdServicos(){
        List<Long> servicosId = new ArrayList<>();
        for(Servico s : this.servicos){
            servicosId.add(s.getId());
        }

        return servicosId;
    }


    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public OrdemServico toEntity(){
        OrdemServico entity = new OrdemServico();
        entity.setDataRegistro(this.getDataRegistro());
        entity.setDataPagamento(this.getDataPagamento());
        entity.setPago(this.isPago());
        entity.setConcluida(this.isConcluida());
        entity.setIdCliente(this.getCliente().getId());
        entity.setIdVeiculo(this.getVeiculo().getId());
        entity.setServicos(this.getIdServicos());
        return entity;
    }

}
