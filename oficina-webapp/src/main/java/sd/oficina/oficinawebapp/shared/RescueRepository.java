package sd.oficina.oficinawebapp.rescue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.oficina.shared.model.EventRescue;

@Repository
public interface RescueRepository extends JpaRepository<EventRescue,String> {

}
