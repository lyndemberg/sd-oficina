package sd.oficina.shared.model.person;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Cidade implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String nome;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private Estado estado;
}
