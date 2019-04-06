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
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Estado> salvar(@RequestBody Estado estado) {
        return ResponseEntity.ok().body(estadoService.salvar(estado));
    }

    @PutMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Estado> atualizar(@RequestBody Estado estado) {
        return ResponseEntity.ok().body(estadoService.atualizar(estado));
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Void> deletar(@PathVariable("id") int idDoEstado) {
        estadoService.deletar(idDoEstado);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Estado> buscar(@PathVariable("id") int idDoEstado) {
        Estado estado = estadoService.buscar(idDoEstado);
        return ResponseEntity.ok().body(estado);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Estado>> listar() {
        return ResponseEntity.ok().body(estadoService.listar());
    }
}
