package sd.oficina.oficinawebapp.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.oficina.oficinawebapp.order.service.OrdemServicoService;
import sd.oficina.oficinawebapp.order.valueobject.OrdemServicoValue;
import sd.oficina.shared.model.order.OrdemServico;
import sd.oficina.shared.model.person.Cliente;

import java.util.List;

@RestController
@RequestMapping("/api/ordemservico")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService){
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Void> cadastrarOrdem(@RequestBody OrdemServicoValue value){
        ordemServicoService.salvar(value);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoValue> getOrdemServico(@PathVariable Long id){
        // @TODO
        return ResponseEntity.ok(null);
    }

    @GetMapping("/cliente/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<OrdemServicoValue>> buscarOrdensCliente(@PathVariable Long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        List<OrdemServicoValue> ordemServicoValueList =
                ordemServicoService.buscarOrdensDeServicoPorCliente(cliente);
        return ResponseEntity.ok(ordemServicoValueList);
    }

}
