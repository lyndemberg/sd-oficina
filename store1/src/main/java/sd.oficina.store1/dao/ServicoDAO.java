package sd.oficina.store1.dao;

import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Servico;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class ServicoDAO implements DAO<Servico> {

    private EntityManager em;

    public ServicoDAO() {
        this.em = Persistence
                .createEntityManagerFactory("Projeto")
                .createEntityManager();
    }

    @Override
    public Servico salvar(Servico obj) {

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
    public Servico atualizar(Servico obj) {
        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
        return buscar(obj.getId());
    }

    @Override
    public void deletar(Object key) {
        em.getTransaction().begin();
        Servico servico = buscar(key);
        if(servico!= null){
            em.remove(servico);
        }
        em.getTransaction().commit();
    }

    @Override
    public Servico buscar(Object key) {
        return em.find(Servico.class, key);
    }

    @Override
    public List<Servico> buscarTodos() {
        return em.createQuery("SELECT s FROM Servico  s").getResultList();
    }
}
