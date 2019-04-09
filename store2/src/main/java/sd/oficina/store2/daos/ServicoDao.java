package sd.oficina.store2.daos;

import sd.oficina.shared.model.store.Servico;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ServicoDao {

    private EntityManager entityManager =
            Persistence.createEntityManagerFactory("persistencia")
                    .createEntityManager();

    public Servico salvar(Servico servico) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(servico);
        transaction.commit();
        return servico;
    }

    public Servico atualizar(Servico servico) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(servico);
        transaction.commit();
        return servico;
    }

    public void deletar(Servico servico) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (!entityManager.contains(servico)) {
            servico = entityManager.merge(servico);
        }

        entityManager.remove(servico);
        transaction.commit();
    }

    public Servico buscar(Servico servico) {
        return entityManager.find(Servico.class, servico.getId());
    }

    public List<Servico> listar() {
        TypedQuery<Servico> query = entityManager
                .createQuery("SELECT servico FROM Servico servico", Servico.class);
        List<Servico> servicos = query.getResultList();
        if (servicos == null) {
            return new ArrayList<>();
        }
        List<Servico> servicosRecuperados= new ArrayList<>();
        for (Servico servico : servicos) {
            servicosRecuperados.add(servico);

        }
        return servicosRecuperados;
    }
}
