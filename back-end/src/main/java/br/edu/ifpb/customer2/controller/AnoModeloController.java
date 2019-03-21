package br.edu.ifpb.customer2.controller;

import br.edu.ifpb.customer2.service.AnoModeloService;
import br.edu.ifpb.model.AnoModelo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("anoModelo")
public class AnoModeloController {
    
    private final AnoModeloService service;

    public AnoModeloController(AnoModeloService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<AnoModelo>> listarTodos() {
        List<AnoModelo> anoModelos = null;
        return anoModelos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(anoModelos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<AnoModelo> buscarAnoModelo(@PathVariable("id") int id) {
        Optional<AnoModelo> anoModelo = null;
        return anoModelo.isPresent() ? ResponseEntity.ok(anoModelo.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<AnoModelo> salvar(@RequestBody AnoModelo anoModelo) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {

        return ResponseEntity.ok().build();
    }
}