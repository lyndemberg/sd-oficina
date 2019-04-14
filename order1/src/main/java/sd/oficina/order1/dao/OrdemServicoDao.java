package sd.oficina.order1.dao;

import sd.oficina.shared.model.order.OrdemServico;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrdemServicoDao {

    private EntityManager entityManager =
            Persistence.createEntityManagerFactory("order1-persistence")
                    .createEntityManager();

    public void salvar(OrdemServico ordemServico) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(ordemServico);
        transaction.commit();
    }

    public OrdemServico buscarPorId(Long id) {
        return entityManager.find(OrdemServico.class, id);
    }

    public List<OrdemServico> buscarOrdensPorCliente(Long idCliente){
        TypedQuery<OrdemServico> query = entityManager.createQuery("SELECT o FROM OrdemServico o WHERE o.idCliente =:idCliente", OrdemServico.class);
        query.setParameter("idCliente",idCliente);
        return query.getResultList();
    }

    public OrdemServico atualizar(OrdemServico obj) {
        OrdemServico updated = null;
        entityManager.getTransaction().begin();
        updated = entityManager.merge(obj);
        entityManager.getTransaction().commit();
        return updated;
    }

    public List<OrdemServico> listarTodos(){
        return entityManager.createQuery("SELECT o FROM OrdemServico o")
                .getResultList();
    }

}
