package sd.oficina.customer2.dao;

import sd.oficina.shared.model.customer.AnoModelo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class AnoModeloDAO implements DAO<AnoModelo> {

    private EntityManager em;

    public AnoModeloDAO(){
        this.em = Persistence
                .createEntityManagerFactory("Projeto")
                .createEntityManager();
    }

    public AnoModelo salvar(AnoModelo obj){
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        return obj;
    }

    public AnoModelo atualizar(AnoModelo obj) {
        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
        return buscar(obj.getId());
    }

    public void deletar(Object key) {
        em.getTransaction().begin();
        em.remove(buscar(key));
        em.getTransaction().commit();
    }

    public AnoModelo buscar(Object key) {
        return em.find(AnoModelo.class, key);
    }

    public List<AnoModelo> todos(){
        return em.createQuery("SELECT a FROM AnoModelo a").getResultList();
    }
}
