package br.edu.ifpb.customer2.controller;

import br.edu.ifpb.customer2.service.ModeloService;
import br.edu.ifpb.model.Modelo;
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
        List<Modelo> modelos = service.todos();
        return modelos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(modelos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Modelo> buscarModelo(@PathVariable("id") int id) {
        Modelo modelo = service.buscar(id);
        return modelo.getNome() != "" ? ResponseEntity.ok(modelo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Modelo> salvar(@RequestBody Modelo modelo) {
        return ResponseEntity.ok(service.salvar(modelo));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<Object> atualizar(@RequestBody Modelo modelo) {
        return ResponseEntity.ok(service.atualizar(modelo));
    }
}
