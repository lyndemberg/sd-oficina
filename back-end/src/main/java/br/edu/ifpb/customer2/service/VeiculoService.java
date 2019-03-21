package br.edu.ifpb.customer2.service;

import br.edu.ifpb.customer2.model.Veiculo;
import br.edu.ifpb.customer2.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public Veiculo salvar(Veiculo veiculo) {
        return repository.save(veiculo);
    }

    public Optional<Veiculo> buscarVeiculo(int id) {
        return repository.findById(id);
    }

    public List<Veiculo> listarTodos() {
        return repository.findAll();
    }

    public void deletar(int id) {
        repository.delete(buscarVeiculo(id).get());
    }


}
