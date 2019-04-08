package sd.oficina.order1.valueobject;

public class OrcamentoItemValue {
    private Integer quantidade;
    private String descricao;
    private String tipo;
    private Float valor;

    public OrcamentoItemValue(Integer quantidade, String descricao, String tipo, Float valor) {
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
    }

    public OrcamentoItemValue() {
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "OrcamentoItemValue{" +
                "quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                '}';
    }
}
