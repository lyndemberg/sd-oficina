package br.edu.ifpb.customer2.repository;

import br.edu.ifpb.customer2.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRepository extends JpaRepository<Modelo, Integer> {
}
