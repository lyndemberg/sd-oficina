package br.edu.ifpb.customer2.service;

import br.edu.ifpb.customer2.model.Fabricante;
import br.edu.ifpb.customer2.repository.FabricanteRepository;
import br.edu.ifpb.customer2.repository.FabricanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FabricanteService {

    private final FabricanteRepository repository;

    public FabricanteService(FabricanteRepository repository) {
        this.repository = repository;
    }

    public Fabricante salvar(Fabricante fabricante) {
        return repository.save(fabricante);
    }

    public Optional<Fabricante> buscar(int id) {
        return repository.findById(id);
    }

    public List<Fabricante> listarTodos() {
        return repository.findAll();
    }

    public void deletar(int id) {
        Optional<Fabricante> fabricante = buscar(id);
        if (fabricante.isPresent()) {
            repository.delete(fabricante.get());
        }
    }
}
