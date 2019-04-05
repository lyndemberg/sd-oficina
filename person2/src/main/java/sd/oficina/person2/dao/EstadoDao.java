package sd.oficina.person2.dao;

import sd.oficina.shared.model.person.Estado;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class EstadoDao implements Dao<Estado>{

    private final EntityManager entity;

    public EstadoDao() {
        this.entity = Persistence
                .createEntityManagerFactory("person2")
                .createEntityManager();
    }

    @Override
    public Estado salvar(Estado obj) {
        entity.getTransaction().begin();
        entity.persist(obj);
        entity.getTransaction().commit();
        return obj;
    }

    @Override
    public Estado atualizar(Estado obj) {
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
    public Estado getById(Object id) {
        return entity.find(Estado.class, id);
    }

    @Override
    public List<Estado> getAll() {
        return entity.createQuery("SELECT a FROM Estado a").getResultList();
    }
}
