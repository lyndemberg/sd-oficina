package br.edu.ifpb.customer2.controller;

import br.edu.ifpb.customer2.model.Modelo;
import br.edu.ifpb.customer2.service.ModeloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("modelo")
public class ModeloController {
    
    private final ModeloService service;

    public ModeloController(ModeloService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Modelo>> listarTodos() {
        List<Modelo> Modelos = service.listarTodos();
        return Modelos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Modelos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Modelo> buscarModelo(@PathVariable("id") int id) {
        Optional<Modelo> Modelo = service.buscar(id);
        return Modelo.isPresent() ? ResponseEntity.ok(Modelo.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Modelo> salvar(@RequestBody Modelo Modelo) {
        return ResponseEntity.ok(service.salvar(Modelo));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}
