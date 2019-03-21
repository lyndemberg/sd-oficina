package br.edu.ifpb.customer2.repository;

import br.edu.ifpb.customer2.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
}
