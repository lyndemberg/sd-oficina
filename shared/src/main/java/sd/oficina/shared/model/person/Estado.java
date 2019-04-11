package sd.oficina.shared.model.person;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Estado implements Serializable {

    @Id
    private long id;
    @Column(nullable = false)
    private String nome;
}
