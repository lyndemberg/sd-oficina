package br.edu.ifpb.customer2.service;

import br.edu.ifpb.customer2.model.Modelo;
import br.edu.ifpb.customer2.repository.ModeloRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {

    private final ModeloRepository repository;

    public ModeloService(ModeloRepository repository) {
        this.repository = repository;
    }

    public Modelo salvar(Modelo modelo) {
        return repository.save(modelo);
    }

    public Optional<Modelo> buscar(int id) {
        return repository.findById(id);
    }

    public List<Modelo> listarTodos() {
        return repository.findAll();
    }

    public void deletar(int id) {
        Optional<Modelo> modelo = buscar(id);
        if (modelo.isPresent()) {
            repository.delete(modelo.get());
        }
    }
}
