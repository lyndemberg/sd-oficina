package sd.oficina.shared.model.person;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@RedisHash("Cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String telefoneFixo;
    private String CEP;
    private int numero;
    private String logradouro;
    private String bairro;
    private String CPF;
    private String complemento;
    private String nome;
    private String telefoneCelular;
    private String email;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private Estado estado;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private Cidade cidade;
}
