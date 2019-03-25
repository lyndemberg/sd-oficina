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
        List<AnoModelo> anoModelos = service.todos();
        return anoModelos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(anoModelos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<AnoModelo> buscarAnoModelo(@PathVariable("id") int id) {
        AnoModelo anoModelo = service.buscar(id);
        return anoModelo.getNome() != "" ? ResponseEntity.ok(anoModelo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<AnoModelo> salvar(@RequestBody AnoModelo anoModelo) {
        return ResponseEntity.ok(service.salvar(anoModelo));
    }

    @PutMapping
    private ResponseEntity<Object> atualizar(@RequestBody AnoModelo anoModelo) {
        return ResponseEntity.ok(service.atualizar(anoModelo));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}