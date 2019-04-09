package sd.oficina.shared.model.store;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@RedisHash("Servico")
public class Servico {

    @Id
    @GeneratedValue
    private Long id;
    private String descricao;
    private double valor;
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private List<Estoque> estoques;
}
