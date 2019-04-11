package sd.oficina.store2.daos;

import sd.oficina.shared.model.store.Estoque;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class NotaDao {

    private EntityManager entityManager =
            Persistence.createEntityManagerFactory("persistencia")
                    .createEntityManager();

    public Estoque salvar(Estoque estoque) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(estoque);
        transaction.commit();
        return estoque;
    }

    public Estoque atualizar(Estoque estoque) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(estoque);
        transaction.commit();
        return estoque;
    }

    public void deletar(Estoque estoque) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (!entityManager.contains(estoque)) {
            estoque = entityManager.merge(estoque);
        }

        entityManager.remove(estoque);
        transaction.commit();
    }

    public Estoque buscar(Estoque estoque) {
        return entityManager.find(Estoque.class, estoque.getIdPeca());
    }

    public List<Estoque> listar() {
        TypedQuery<Estoque> query = entityManager
                .createQuery("SELECT estoque FROM Estoque estoque", Estoque.class);
        List<Estoque> estoques = query.getResultList();
        if (estoques == null) {
            return new ArrayList<>();
        }
        List<Estoque> estoquesRecuperados= new ArrayList<>();
        for (Estoque estoque : estoques) {
            estoquesRecuperados.add(estoque);

        }
        return estoquesRecuperados;
    }
}
