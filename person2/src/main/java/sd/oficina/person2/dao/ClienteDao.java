package sd.oficina.person2.dao;

import sd.oficina.shared.model.person.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class ClienteDao implements Dao<Cliente>{

    private final EntityManager entity;

    public ClienteDao() {
        this.entity = Persistence
                .createEntityManagerFactory("person2")
                .createEntityManager();
    }

    @Override
    public Cliente salvar(Cliente obj) {
        entity.getTransaction().begin();
        entity.persist(obj);
        entity.getTransaction().commit();
        return obj;
    }

    @Override
    public Cliente atualizar(Cliente obj) {
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
    public Cliente getById(Object id) {
        return entity.find(Cliente.class, id);
    }

    @Override
    public List<Cliente> getAll() {
        return entity.createQuery("SELECT a FROM Cliente a").getResultList();
    }
}
