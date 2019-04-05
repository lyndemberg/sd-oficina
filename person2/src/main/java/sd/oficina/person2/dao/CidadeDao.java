package sd.oficina.person2.dao;

import sd.oficina.shared.model.person.Cidade;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class CidadeDao implements Dao<Cidade> {

    private final EntityManager entity;

    public CidadeDao() {
        this.entity = Persistence
                .createEntityManagerFactory("person2")
                .createEntityManager();
    }

    @Override
    public Cidade salvar(Cidade obj) {
        entity.getTransaction().begin();
        entity.persist(obj);
        entity.getTransaction().commit();
        return obj;
    }

    @Override
    public Cidade atualizar(Cidade obj) {
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
    public Cidade getById(Object id) {
        return entity.find(Cidade.class, id);
    }

    @Override
    public List<Cidade> getAll() {
        return entity.createQuery("SELECT a FROM Cidade a").getResultList();
    }
}
