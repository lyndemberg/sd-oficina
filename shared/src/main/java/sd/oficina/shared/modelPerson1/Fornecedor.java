package sd.oficina.shared.modelPerson1;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Fornecedor {

    @Id
    @GeneratedValue
    private int id;
    private String nomeFantasia;
    private String razaoSocial;
    private String vendedor;
    private int codigo;
    private String CNPJ;
    private String logradouro;
    private int numero;
    private String telefone;
    private String complemento;
    private String bairro;
    private String CEP;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Cidade cidade;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Estado estado;

}
