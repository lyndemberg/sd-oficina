package br.edu.ifpb.customer2.model;

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
public class Fabricante {

    @Id
    @GeneratedValue
    private int id;
    private String nome;
}
