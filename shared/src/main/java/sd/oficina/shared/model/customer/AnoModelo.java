package sd.oficina.shared.model.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AnoModelo {

    @Id
    @GeneratedValue
    private long id;
    private String nome;
    private String tipo;
    private double valor;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Modelo modelo;
}
