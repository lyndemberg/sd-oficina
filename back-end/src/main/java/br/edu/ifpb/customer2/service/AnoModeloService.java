package br.edu.ifpb.customer2.service;

import br.edu.ifpb.customer2.model.AnoModelo;
import br.edu.ifpb.customer2.model.Modelo;
import br.edu.ifpb.customer2.repository.AnoModeloRepository;
import br.edu.ifpb.customer2.repository.ModeloRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnoModeloService {

    private final AnoModeloRepository repository;
    private final ModeloRepository modeloRepository;

    public AnoModeloService(AnoModeloRepository repository, ModeloRepository modeloRepository) {
        this.repository = repository;
        this.modeloRepository = modeloRepository;
    }

    public AnoModelo salvar(AnoModelo anoModelo) {
        Optional<Modelo> modeloOPtional = modeloRepository.findById(anoModelo.getModelo().getId());
        if(modeloOPtional.isPresent()){
            anoModelo.setModelo(modeloOPtional.get());
        }
        return repository.save(anoModelo);
    }

    public Optional<AnoModelo> buscar(int id) {
        return repository.findById(id);
    }

    public List<AnoModelo> listarTodos() {
        return repository.findAll();
    }

    public void deletar(int id) {
        Optional<AnoModelo> anoModelo = buscar(id);
        if (anoModelo.isPresent()) {
            repository.delete(anoModelo.get());
        }
    }
}
