package sd.oficina.oficinawebapp.person.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.oficina.oficinawebapp.person.services.FornecedorService;
import sd.oficina.shared.model.person.Fornecedor;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedor")
public class FornecedorController {

    private FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Fornecedor> salvar(@RequestBody Fornecedor fornecedor) {
        return ResponseEntity.ok().body(fornecedorService.salvar(fornecedor));
    }

    @PutMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Fornecedor> atualizar(@RequestBody Fornecedor fornecedor) {
        return ResponseEntity.ok().body(fornecedorService.atualizar(fornecedor));
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Void> deletar(@PathVariable("id") int idDoFornecedor) {
        fornecedorService.deletar(idDoFornecedor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Fornecedor> buscar(@PathVariable("id") int idDoFornecedor) {
        Fornecedor fornecedor = fornecedorService.buscar(idDoFornecedor);
        return ResponseEntity.ok().body(fornecedor);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Fornecedor>> listar() {
        return ResponseEntity.ok().body(fornecedorService.listar());
    }
}
