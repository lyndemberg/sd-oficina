package sd.oficina.shared.model.person;

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
