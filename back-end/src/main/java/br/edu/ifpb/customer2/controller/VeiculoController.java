package br.edu.ifpb.customer2.controller;

import br.edu.ifpb.customer2.model.Veiculo;
import br.edu.ifpb.customer2.service.VeiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("veiculo")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Veiculo>> listarTodos() {
        List<Veiculo> veiculos = service.listarTodos();
        return veiculos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(veiculos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Veiculo> buscarVeiculo(@PathVariable("id") int id) {
        Optional<Veiculo> veiculo = service.buscarVeiculo(id);
        return veiculo.isPresent() ? ResponseEntity.ok(veiculo.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Veiculo> salvar(@RequestBody Veiculo veiculo) {
        return ResponseEntity.ok(service.salvar(veiculo));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}
