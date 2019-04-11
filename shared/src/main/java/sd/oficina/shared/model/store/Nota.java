package sd.oficina.shared.model.store;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Nota {

    @Id
    private long id;
    private LocalDate dataCompra;
    private LocalDate dataVencimento;
    private int numero;
    private long idFornecedor;
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private List<Estoque> estoques;
}
