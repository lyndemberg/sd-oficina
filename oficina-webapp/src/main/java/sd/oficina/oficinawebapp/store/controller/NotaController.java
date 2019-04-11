package sd.oficina.oficinawebapp.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.oficina.oficinawebapp.store.service.NotaService;
import sd.oficina.shared.model.store.Nota;

import java.util.List;

@RequestMapping("nota")
@RestController
public class NotaController {
    
    private final NotaService service;

    public NotaController(NotaService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Nota>> listarTodos() {
        List<Nota> Notas = service.todos();
        return Notas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Notas);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Nota> buscarNota(@PathVariable("id") int id) {
        Nota Nota = service.buscar(id);
        return Nota.getId() != 0 ? ResponseEntity.ok(Nota) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Object> salvar(@RequestBody Nota nota) {
        return ResponseEntity.ok(service.salvar(nota));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<Object> atualizar(@RequestBody Nota nota) {
        return ResponseEntity.ok(service.atualizar(nota));
    }
}

