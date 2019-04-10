package sd.oficina.person1.config;

import lombok.Data;

import java.util.List;

@Data
public class EstadoJSON {

    private String sigla;
    private String nome;
    private List<String> cidades;
}
