package sd.oficina.oficinawebapp.identity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.oficina.shared.model.Sequence;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence,String> {
}
