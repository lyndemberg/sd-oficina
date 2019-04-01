package sd.oficina.person1.daos;

import sd.oficina.shared.modelPerson1.Estado;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class EstadoDao implements Dao<Estado> {

    private EntityManager entityManager =
                Persistence.createEntityManagerFactory("persistencia")
                        .createEntityManager();

    @Override
    public void salvar(Estado estado) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(estado);
        transaction.commit();
    }

    @Override
    public void atualizar(Estado estado) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(estado);
        transaction.commit();
    }

    @Override
    public void deletar(Estado estado) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (!entityManager.contains(estado)) {
            estado = entityManager.merge(estado);
        }

        entityManager.remove(estado);
        transaction.commit();
    }

    @Override
    public Estado buscar(Estado estado) {
        return entityManager.find(Estado.class, estado.getId());
    }

    @Override
    public List<Estado> listar() {
        TypedQuery<Estado> query = entityManager
                .createQuery("SELECT estado FROM Estado estado", Estado.class);
        List<Estado> estados = query.getResultList();
        if (estados == null) {
            return new ArrayList<>();
        }
        List<Estado> estadosRecuperadas= new ArrayList<>();
        for (Estado estado : estados) {
            estadosRecuperadas.add(estado);

        }
        return estadosRecuperadas;
    }
}
