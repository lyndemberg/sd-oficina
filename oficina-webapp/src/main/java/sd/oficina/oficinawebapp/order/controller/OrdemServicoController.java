package sd.oficina.oficinawebapp.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sd.oficina.oficinawebapp.order.service.OrdemServicoService;

@RestController
@RequestMapping("ordemservico")
public class OrdemServicoController {
    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }
}
