package sd.oficina.oficinawebapp.person.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.oficina.oficinawebapp.person.services.EstadoService;
import sd.oficina.shared.model.person.Estado;

import java.util.List;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {

    private EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @PostMapping
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado) {
        return ResponseEntity.ok().body(estadoService.salvar(estado));
    }

    @PutMapping
    public ResponseEntity<Estado> atualizar(@RequestBody Estado estado) {
        return ResponseEntity.ok().body(estadoService.atualizar(estado));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@RequestParam("id") int idDoEstado) {
        estadoService.deletar(idDoEstado);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id")
    public ResponseEntity<Estado> buscar(@RequestParam("id") int idDoEstado) {
        Estado estado = estadoService.buscar(idDoEstado);
        return ResponseEntity.ok().body(estado);
    }

    @GetMapping
    public ResponseEntity<List<Estado>> listar() {
        return ResponseEntity.ok().body(estadoService.listar());
    }
}
