package sd.oficina.shared.model.order;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
public class OrdemServico implements Serializable {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Id
    private long id;
    private Long idCliente;
    private Long idVeiculo;
    private LocalDate dataRegistro;
    private LocalDate dataPagamento;
    private boolean pago;
    private boolean concluida;
    @ElementCollection
    private List<Long> servicos;

    public OrdemServico() {

    }

    public OrdemServico(long id, Long idCliente, Long idVeiculo, LocalDate dataRegistro, LocalDate dataPagamento, boolean pago, boolean concluida, List<Long> servicos) {
        this.id = id;
        this.idCliente = idCliente;
        this.idVeiculo = idVeiculo;
        this.dataRegistro = dataRegistro;
        this.dataPagamento = dataPagamento;
        this.pago = pago;
        this.concluida = concluida;
        this.servicos = servicos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonGetter("dataRegistro")
    public String getDataRegistroJson() {
        return formatter.format(this.dataRegistro);
    }

    @JsonSetter("dataRegistro")
    public void setDataRegistroJson(String dataRegistro) {
        this.dataRegistro = LocalDate.parse(dataRegistro, formatter);
    }

    @JsonGetter("dataPagamento")
    public String getDataPagamentoJson() {
        return formatter.format(dataPagamento);
    }

    @JsonSetter("dataPagamento")
    public void setDataPagamentoJson(String dataPagamento) {
        this.dataPagamento = LocalDate.parse(dataPagamento, formatter);
    }
}
