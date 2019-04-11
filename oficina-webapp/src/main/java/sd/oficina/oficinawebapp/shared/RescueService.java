package sd.oficina.oficinawebapp.shared;

import org.springframework.beans.factory.annotation.Autowired;
import sd.oficina.shared.model.EventRescue;

public class RescueService {

    @Autowired
    private sd.oficina.oficinawebapp.rescue.RescueRepository rescueRepository;
    private final Object lock;

    public RescueService() {
        this.lock = new Object();
    }

    public EventRescue adicionarEventoNoRescue(EventRescue event){
        //garantindo a ordenação dos eventos inseridos na tabela
        synchronized (lock){
            EventRescue save = rescueRepository.save(event);
            return save;
        }
    }
}
