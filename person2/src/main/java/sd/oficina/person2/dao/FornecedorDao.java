package sd.oficina.person2.dao;

import sd.oficina.shared.model.person.Fornecedor;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class FornecedorDao implements Dao<Fornecedor>{

    private final EntityManager entity;

    public FornecedorDao() {
        this.entity = Persistence
                .createEntityManagerFactory("person2")
                .createEntityManager();
    }

    @Override
    public Fornecedor salvar(Fornecedor obj) {
        entity.getTransaction().begin();
        entity.persist(obj);
        entity.getTransaction().commit();
        return obj;
    }

    @Override
    public Fornecedor atualizar(Fornecedor obj) {
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
    public Fornecedor getById(Object id) {
        return entity.find(Fornecedor.class, id);
    }

    @Override
    public List<Fornecedor> getAll() {
        return entity.createQuery("SELECT a FROM Fornecedor a").getResultList();
    }
}
