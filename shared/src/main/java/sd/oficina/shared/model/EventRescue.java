package sd.oficina.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRescue implements Serializable {

    /*
        Essa entidade é genérica o suficiente para que o determinado serviço execute
        o procedimento posteriormente.
        Todos os atributos são opcionais, pq nem sempre é necessário preeencher todos os campos
        Atributos
         - SERVICE: descreve o nome do serviço para quem a execução é encaminhada
         - ACTION: define a ação a ser executada
         - entity: nome da classe entidade envolvida (por padrão use Classe.class.getSimpleName())
         - payload: é uma string que representa um JSON com os dados necessários para
                    a execução da ação

     */

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private ServiceEnum service;

    @Enumerated(EnumType.STRING)
    private ActionEnum action;

    private String entity;

    @Lob
    private String payload;

}
