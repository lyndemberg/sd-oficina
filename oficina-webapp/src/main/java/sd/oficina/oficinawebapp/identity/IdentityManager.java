package sd.oficina.oficinawebapp.identity;

import org.springframework.beans.factory.annotation.Autowired;
import sd.oficina.shared.model.Sequence;

import java.util.Optional;

public class IdentityManager {

    @Autowired
    private SequenceRepository sequenceRepository;
    private final Object lock;

    public IdentityManager() {
        this.lock = new Object();
    }

    public long gerarIdParaEntidade(String entityName){
        long id = 0;
        //
        synchronized (lock){
            Optional<Sequence> optionalSequence = sequenceRepository.findById(entityName);
            if(optionalSequence.isPresent()){
                Sequence sequence = optionalSequence.get();
                id = sequence.getLastId() + 1;
            }else{
                id = id + 1;
            }
            atualizarId(entityName, id);
        }
        //
        return id;
    }


    private void atualizarId(String entityName, long id){
        Sequence sequence = new Sequence();
        sequence.setEntity(entityName);
        sequence.setLastId(id);
        sequenceRepository.save(sequence);
    }
}
