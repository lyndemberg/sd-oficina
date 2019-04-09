package sd.oficina.shared.model.store;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@RedisHash("Estoque")
public class Estoque {

    @Id
    @GeneratedValue
    private Long idPeca;
    private long qtdPeca;
    private int codigoPeca;
    private String validade;
    private double valorPeca;
    private String nomePeca;
}
