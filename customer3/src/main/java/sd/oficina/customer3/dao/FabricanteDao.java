package sd.oficina.customer3.dao;

import sd.oficina.shared.model.customer.Fabricante;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class FabricanteDao implements Dao<Fabricante> {

    private final EntityManager entity;

    public FabricanteDao() {
        this.entity = Persistence
                .createEntityManagerFactory("customer3")
                .createEntityManager();
    }

    @Override
    public Fabricante salvar(Fabricante obj) {
        entity.getTransaction().begin();
        entity.persist(obj);
        entity.getTransaction().commit();
        return obj;
    }

    @Override
    public Fabricante atualizar(Fabricante obj) {
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
    public Fabricante getById(Object id) {
        return entity.find(Fabricante.class, id);
    }

    @Override
    public List<Fabricante> getAll() {
        return entity.createQuery("SELECT a FROM Fabricante a").getResultList();
    }
}
