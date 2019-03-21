package br.edu.ifpb.customer2.controller;

import br.edu.ifpb.customer2.service.FabricanteService;
import br.edu.ifpb.model.Fabricante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("fabricante")
public class FabricanteController {
    
    private final FabricanteService service;

    public FabricanteController(FabricanteService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Fabricante>> listarTodos() {
        List<Fabricante> fabricantes = null;
        return fabricantes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(fabricantes);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Fabricante> buscarFabricante(@PathVariable("id") int id) {
        Optional<Fabricante> fabricante = null;
        return fabricante.isPresent() ? ResponseEntity.ok(fabricante.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Fabricante> salvar(@RequestBody Fabricante fabricante) {
        return ResponseEntity.ok(service.salvar(fabricante));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {

        return ResponseEntity.ok().build();
    }
}
