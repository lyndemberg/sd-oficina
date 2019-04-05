package sd.oficina.customer3.dao;

import sd.oficina.shared.model.customer.Veiculo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class VeiculoDao implements Dao<Veiculo> {

    private final EntityManager entity;

    public VeiculoDao() {
        this.entity = Persistence
                .createEntityManagerFactory("customer3")
                .createEntityManager();
    }

    @Override
    public Veiculo salvar(Veiculo obj) {
        entity.getTransaction().begin();
        entity.persist(obj);
        entity.getTransaction().commit();
        return obj;
    }

    @Override
    public Veiculo atualizar(Veiculo obj) {
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
    public Veiculo getById(Object id) {
        return entity.find(Veiculo.class, id);
    }

    @Override
    public List<Veiculo> getAll() {
        return entity.createQuery("SELECT a FROM Veiculo a").getResultList();
    }
}
