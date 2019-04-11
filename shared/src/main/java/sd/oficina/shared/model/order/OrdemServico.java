package sd.oficina.shared.model.order;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class OrdemServico implements Serializable {

    @Id
    private long id;
    private Long idCliente;
    private Long idVeiculo;
    private LocalDate dataRegistro;
    private LocalDate dataPagamento;
    private boolean pago;
    private boolean concluida;
    @ElementCollection
    private List<Long> servicos;

}
