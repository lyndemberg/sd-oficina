package sd.oficina.store1.dao;

import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Nota;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class NotaDAO implements DAO<Nota> {
    private EntityManager em;

    public NotaDAO() {
        this.em = Persistence
                .createEntityManagerFactory("Projeto")
                .createEntityManager();
    }

    @Override
    public Nota salvar(Nota obj) {
        em.getTransaction().begin();
        for (int k = 0; k < obj.getEstoques().size(); k++) {
            Estoque e = obj.getEstoques().get(k);
            if (em.find(Estoque.class, e.getIdPeca()) == null) {
                em.persist(e);
            } else {
                obj.getEstoques().set(k, em.merge(e));
            }
        }
        em.persist(obj);
        em.getTransaction().commit();
        return obj;
    }

    @Override
    public Nota atualizar(Nota obj) {
        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
        return obj;
    }

    @Override
    public void deletar(Object key) {
        em.getTransaction().begin();
        em.remove(buscar(key));
        em.getTransaction().commit();
    }

    @Override
    public Nota buscar(Object key) {
        return em.find(Nota.class, key);
    }

    @Override
    public List<Nota> buscarTodos() {
        return em.createQuery("SELECT n FROM Nota n").getResultList();
    }
}
