package sd.oficina.oficinawebapp.customer.controller;

import sd.oficina.oficinawebapp.customer.service.VeiculoService;
import sd.oficina.shared.model.customer.Veiculo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("veiculo")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Veiculo>> listarTodos() {
        List<Veiculo> Veiculos = service.todos();
        return Veiculos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(Veiculos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Veiculo> buscarVeiculo(@PathVariable("id") int id) {
        Veiculo veiculo = service.buscar(id);
        return veiculo.getId() != 0? ResponseEntity.ok(veiculo) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Object> salvar(@RequestBody Veiculo veiculo) {
        return ResponseEntity.ok(service.salvar(veiculo));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<Object> atualizar(@RequestBody Veiculo veiculo) {
        return ResponseEntity.ok(service.atualizar(veiculo));
    }
}
