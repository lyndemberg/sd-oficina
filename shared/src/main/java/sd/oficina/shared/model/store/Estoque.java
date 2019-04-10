package sd.oficina.shared.model.store;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Estoque {

    @Id
    @GeneratedValue
    private long idPeca;
    private int qtdPeca;
    private int codigoPeca;
    private String validade;
    private double valorPeca;
    private String nomePeca;
}
