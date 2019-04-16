package sd.oficina.person1.daos;

import sd.oficina.shared.model.person.Estado;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class EstadoDao {

    private EntityManager entityManager =
                Persistence.createEntityManagerFactory("persistencia")
                        .createEntityManager();

    public Estado salvar(Estado estado) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Estado estadoSalvo = entityManager.merge(estado);
        transaction.commit();

        return estadoSalvo;
    }

    public Estado atualizar(Estado estado) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(estado);
        transaction.commit();
        return estado;
    }

    public void deletar(Estado estado) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (!entityManager.contains(estado)) {
            estado = entityManager.merge(estado);
        }

        entityManager.remove(estado);
        transaction.commit();
    }

    public Estado buscar(Estado estado) {
        return entityManager.find(Estado.class, estado.getId());
    }

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
