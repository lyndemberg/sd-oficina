package sd.oficina.oficinawebapp.person.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.oficina.oficinawebapp.person.services.CidadeService;
import sd.oficina.shared.model.person.Cidade;

import java.util.List;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {

    private CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @PostMapping
    public ResponseEntity<Cidade> salvar(@RequestBody Cidade cidade) {
        return ResponseEntity.ok().body(cidadeService.salvar(cidade));
    }

    @PutMapping
    public ResponseEntity<Cidade> atualizar(@RequestBody Cidade cidade) {
        return ResponseEntity.ok().body(cidadeService.atualizar(cidade));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@RequestParam("id") int idDaCidade) {
        cidadeService.deletar(idDaCidade);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id")
    public ResponseEntity<Cidade> buscar(@RequestParam("id") int idDaCidade) {
        Cidade cidade = cidadeService.buscar(idDaCidade);
        return ResponseEntity.ok().body(cidade);
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> listar() {
        return ResponseEntity.ok().body(cidadeService.listar());
    }
}
