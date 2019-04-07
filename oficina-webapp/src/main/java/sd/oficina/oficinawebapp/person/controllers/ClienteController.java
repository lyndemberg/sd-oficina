package sd.oficina.oficinawebapp.person.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.oficina.oficinawebapp.person.services.ClienteService;
import sd.oficina.shared.model.person.Cliente;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok().body(clienteService.salvar(cliente));
    }

    @PutMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Cliente> atualizar(@RequestBody Cliente cliente) {
        return ResponseEntity.ok().body(clienteService.atualizar(cliente));
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Void> deletar(@PathVariable("id") int idDoCliente) {
        clienteService.deletar(idDoCliente);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Cliente> buscar(@PathVariable("id") int idDoCliente) {
        Cliente cliente = clienteService.buscar(idDoCliente);
        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok().body(clienteService.listar());
    }
}
