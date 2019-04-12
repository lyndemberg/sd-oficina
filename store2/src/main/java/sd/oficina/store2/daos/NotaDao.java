package sd.oficina.store2.daos;

import sd.oficina.shared.model.store.Nota;

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

    public Nota salvar(Nota nota) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(nota);
        transaction.commit();
        return nota;
    }

    public Nota atualizar(Nota nota) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(nota);
        transaction.commit();
        return nota;
    }

    public void deletar(Nota nota) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (!entityManager.contains(nota)) {
            nota = entityManager.merge(nota);
        }

        entityManager.remove(nota);
        transaction.commit();
    }

    public Nota buscar(Nota nota) {
        return entityManager.find(Nota.class, nota.getId());
    }

    public List<Nota> listar() {
        TypedQuery<Nota> query = entityManager
                .createQuery("SELECT nota FROM Nota nota", Nota.class);
        List<Nota> notas = query.getResultList();
        if (notas == null) {
            return new ArrayList<>();
        }
        List<Nota> notasRecuperadas= new ArrayList<>();
        for (Nota nota : notas) {
            notasRecuperadas.add(nota);

        }
        return notasRecuperadas;
    }
}
