package sd.oficina.store1.dao;

import sd.oficina.shared.model.store.Estoque;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class EstoqueDAO implements DAO<Estoque> {

    private EntityManager em;

    public EstoqueDAO() {
        this.em = Persistence
                .createEntityManagerFactory("Projeto")
                .createEntityManager();
    }

    @Override
    public Estoque salvar(Estoque obj) {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        return obj;
    }

    @Override
    public Estoque atualizar(Estoque obj) {
        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
        return buscar(obj.getIdPeca());
    }

    @Override
    public void deletar(Object key) {
        em.getTransaction().begin();
        Estoque estoque = buscar(key);
        if(estoque != null){
            em.remove(estoque);
        }
        em.getTransaction().commit();
    }

    @Override
    public Estoque buscar(Object key) {
        return em.find(Estoque.class, key);
    }

    @Override
    public List<Estoque> buscarTodos() {
        return em.createQuery("SELECT e FROM Estoque e").getResultList();
    }
}
