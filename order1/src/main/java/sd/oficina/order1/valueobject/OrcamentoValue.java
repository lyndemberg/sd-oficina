package sd.oficina.order1.valueobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoValue implements Serializable {

    private String nomeInteressado;
    private String cpfInteressado;
    private Double total;
    private List<OrcamentoItemValue> itens;

    public OrcamentoValue() {
        this.itens = new ArrayList<>();
    }

    public OrcamentoValue(String nomeInteressado, String cpfInteressado, Double total, List<OrcamentoItemValue> itens) {
        this.nomeInteressado = nomeInteressado;
        this.cpfInteressado = cpfInteressado;
        this.total = total;
        this.itens = itens;
    }

    public String getNomeInteressado() {
        return nomeInteressado;
    }

    public void setNomeInteressado(String nomeInteressado) {
        this.nomeInteressado = nomeInteressado;
    }

    public String getCpfInteressado() {
        return cpfInteressado;
    }

    public void setCpfInteressado(String cpfInteressado) {
        this.cpfInteressado = cpfInteressado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<OrcamentoItemValue> getItens() {
        return itens;
    }

    public void setItens(List<OrcamentoItemValue> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "OrcamentoValue{" +
                "nomeInteressado='" + nomeInteressado + '\'' +
                ", cpfInteressado='" + cpfInteressado + '\'' +
                ", total=" + total +
                '}';
    }
}
