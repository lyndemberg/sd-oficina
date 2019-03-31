package sd.oficina.shared.modelPerson1;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Estado {

    @Id
    @GeneratedValue
    private int id;
    private String nome;
}
