package sd.oficina.person1.daos;

import sd.oficina.shared.modelPerson1.Fornecedor;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDao implements Dao<Fornecedor> {

    private EntityManager entityManager =
                Persistence.createEntityManagerFactory("persistencia")
                        .createEntityManager();

    @Override
    public void salvar(Fornecedor fornecedor) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(fornecedor);
        transaction.commit();
    }

    @Override
    public void atualizar(Fornecedor fornecedor) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(fornecedor);
        transaction.commit();
    }

    @Override
    public void deletar(Fornecedor fornecedor) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (!entityManager.contains(fornecedor)) {
            fornecedor = entityManager.merge(fornecedor);
        }

        entityManager.remove(fornecedor);
        transaction.commit();
    }

    @Override
    public Fornecedor buscar(Fornecedor fornecedor) {
        return entityManager.find(Fornecedor.class, fornecedor.getId());
    }

    @Override
    public List<Fornecedor> listar() {
        TypedQuery<Fornecedor> query = entityManager
                .createQuery("SELECT fornecedor FROM Fornecedor fornecedor", Fornecedor.class);
        List<Fornecedor> fornecedores = query.getResultList();
        if (fornecedores == null) {
            return new ArrayList<>();
        }
        List<Fornecedor> fornecedoresRecuperadas= new ArrayList<>();
        for (Fornecedor fornecedor : fornecedores) {
            fornecedoresRecuperadas.add(fornecedor);

        }
        return fornecedoresRecuperadas;
    }
}
