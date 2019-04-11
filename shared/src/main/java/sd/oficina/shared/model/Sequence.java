package sd.oficina.shared.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Sequence implements Serializable {

    @Id
    private String entity;
    private long lastId;

}
