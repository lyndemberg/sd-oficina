package br.edu.ifpb.customer2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue
    private int id;
    private String placa;
    private double quilometragem;
}
