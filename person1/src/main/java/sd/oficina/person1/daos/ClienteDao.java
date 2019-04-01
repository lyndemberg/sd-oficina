package sd.oficina.person1.daos;

import sd.oficina.shared.modelPerson1.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao implements Dao<Cliente> {

    private EntityManager entityManager =
                Persistence.createEntityManagerFactory("persistencia")
                        .createEntityManager();

    @Override
    public void salvar(Cliente cliente) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(cliente);
        transaction.commit();
    }

    @Override
    public void atualizar(Cliente cliente) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(cliente);
        transaction.commit();
    }

    @Override
    public void deletar(Cliente cliente) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }

        entityManager.remove(cliente);
        transaction.commit();
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        return entityManager.find(Cliente.class, cliente.getId());
    }

    @Override
    public List<Cliente> listar() {
        TypedQuery<Cliente> query = entityManager
                .createQuery("SELECT cliente FROM Cliente cliente", Cliente.class);
        List<Cliente> clientes = query.getResultList();
        if (clientes == null) {
            return new ArrayList<>();
        }
        List<Cliente> clientesRecuperadas= new ArrayList<>();
        for (Cliente cliente : clientes) {
            clientesRecuperadas.add(cliente);

        }
        return clientesRecuperadas;
    }
}
