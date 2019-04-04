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
    public ResponseEntity<Fornecedor> salvar(@RequestBody Fornecedor fornecedor) {
        return ResponseEntity.ok().body(fornecedorService.salvar(fornecedor));
    }

    @PutMapping
    public ResponseEntity<Fornecedor> atualizar(@RequestBody Fornecedor fornecedor) {
        return ResponseEntity.ok().body(fornecedorService.atualizar(fornecedor));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@RequestParam("id") int idDoFornecedor) {
        fornecedorService.deletar(idDoFornecedor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id")
    public ResponseEntity<Fornecedor> buscar(@RequestParam("id") int idDoFornecedor) {
        Fornecedor fornecedor = fornecedorService.buscar(idDoFornecedor);
        return ResponseEntity.ok().body(fornecedor);
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listar() {
        return ResponseEntity.ok().body(fornecedorService.listar());
    }
}
