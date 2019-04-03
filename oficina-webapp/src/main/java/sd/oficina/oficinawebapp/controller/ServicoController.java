package sd.oficina.oficinawebapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.oficina.oficinawebapp.service.ServicoService;
import sd.oficina.shared.model.store.Servico;

import java.util.List;

@RestController
@RequestMapping("servico")
public class ServicoController {

    private ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Servico>> listarTodos() {
        List<Servico> Servicos = service.todos();
        return Servicos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Servicos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Servico> buscarServico(@PathVariable("id") int id) {
        Servico Servico = service.buscar(id);
        return Servico.getId() != 0 ? ResponseEntity.ok(Servico) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Object> salvar(@RequestBody Servico Servico) {
        return ResponseEntity.ok(service.salvar(Servico));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<Object> atualizar(@RequestBody Servico Servico) {
        return ResponseEntity.ok(service.atualizar(Servico));
    }
}
