package sd.oficina.shared.model.person;

import lombok.Data;
import org.omg.PortableServer.ServantActivator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Fornecedor implements Serializable {

    @Id
    private long id;
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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private Cidade cidade;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private Estado estado;

}
