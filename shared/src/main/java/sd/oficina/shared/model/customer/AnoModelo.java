package sd.oficina.shared.model.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash("AnoModelo")
public class AnoModelo {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String tipo;
    private double valor;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Modelo modelo;
}
