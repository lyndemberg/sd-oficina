package sd.oficina.oficinawebapp.controller;

import sd.oficina.oficinawebapp.service.FabricanteService;
import sd.oficina.shared.model.customer.Fabricante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fabricante")
public class FabricanteController {
    
    private final FabricanteService service;

    public FabricanteController(FabricanteService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Fabricante>> listarTodos() {
        List<Fabricante> fabricantes = service.todos();
        return fabricantes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(fabricantes);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Fabricante> buscarFabricante(@PathVariable("id") int id) {
        Fabricante fabricante = service.buscar(id);
        return fabricante.getId() != 0? ResponseEntity.ok(fabricante) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Object> salvar(@RequestBody Fabricante fabricante) {
        return ResponseEntity.ok(service.salvar(fabricante));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<Object> atualizar(@RequestBody Fabricante fabricante) {
        return ResponseEntity.ok(service.atualizar(fabricante));
    }
}
