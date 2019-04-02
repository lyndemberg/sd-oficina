package sd.oficina.oficinawebapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.oficina.oficinawebapp.service.EstoqueService;
import sd.oficina.shared.model.store.Estoque;

import java.util.List;

@RestController
@RequestMapping("estoque")
public class EstoqueController {

    private EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Estoque>> listarTodos() {
        List<Estoque> Estoques = service.todos();
        return Estoques.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Estoques);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Estoque> buscarEstoque(@PathVariable("id") int id) {
        Estoque Estoque = service.buscar(id);
        return Estoque.getIdPeca() != 0 ? ResponseEntity.ok(Estoque) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Object> salvar(@RequestBody Estoque Estoque) {
        return ResponseEntity.ok(service.salvar(Estoque));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<Object> atualizar(@RequestBody Estoque Estoque) {
        return ResponseEntity.ok(service.atualizar(Estoque));
    }
}
