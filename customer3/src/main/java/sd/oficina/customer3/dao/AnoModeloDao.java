package sd.oficina.customer3.dao;

import sd.oficina.shared.model.customer.AnoModelo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class AnoModeloDao implements Dao<AnoModelo>{

    private final EntityManager entity;

    public AnoModeloDao() {
        this.entity = Persistence
                .createEntityManagerFactory("customer3")
                .createEntityManager();
    }

    @Override
    public AnoModelo salvar(AnoModelo obj) {
        entity.getTransaction().begin();
        entity.persist(obj);
        entity.getTransaction().commit();
        return obj;
    }

    @Override
    public AnoModelo atualizar(AnoModelo obj) {
        entity.getTransaction().begin();
        entity.merge(obj);
        entity.getTransaction().commit();
        return getById(obj.getId());
    }

    @Override
    public void remover(Object id) {
        entity.getTransaction().begin();
        entity.remove(getById(id));
        entity.getTransaction().commit();
    }

    @Override
    public AnoModelo getById(Object id) {
        return entity.find(AnoModelo.class, id);
    }

    @Override
    public List<AnoModelo> getAll() {
        return entity.createQuery("SELECT a FROM AnoModelo a").getResultList();
    }

}
