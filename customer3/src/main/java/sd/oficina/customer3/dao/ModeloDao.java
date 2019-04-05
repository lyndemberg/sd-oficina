package sd.oficina.customer3.dao;

import sd.oficina.shared.model.customer.Fabricante;
import sd.oficina.shared.model.customer.Modelo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class ModeloDao implements Dao<Modelo> {

    private final EntityManager entity;

    public ModeloDao() {
        this.entity = Persistence
                .createEntityManagerFactory("customer3")
                .createEntityManager();
    }

    @Override
    public Modelo salvar(Modelo obj) {
        entity.getTransaction().begin();
        entity.persist(obj);
        entity.getTransaction().commit();
        return obj;
    }

    @Override
    public Modelo atualizar(Modelo obj) {
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
    public Modelo getById(Object id) {
        return entity.find(Modelo.class, id);
    }

    @Override
    public List<Modelo> getAll() {
        return entity.createQuery("SELECT a FROM Modelo a").getResultList();
    }
}
