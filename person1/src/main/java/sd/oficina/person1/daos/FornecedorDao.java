package sd.oficina.person1.daos;

import sd.oficina.shared.model.person.Fornecedor;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDao {

    private EntityManager entityManager =
                Persistence.createEntityManagerFactory("persistencia")
                        .createEntityManager();

    public Fornecedor salvar(Fornecedor fornecedor) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(fornecedor);
        transaction.commit();
        return fornecedor;
    }

    public Fornecedor atualizar(Fornecedor fornecedor) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(fornecedor);
        transaction.commit();
        return fornecedor;
    }

    public void deletar(Fornecedor fornecedor) {

        fornecedor.setEstado(null);
        fornecedor.setCidade(null);
        fornecedor = atualizar(fornecedor);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (!entityManager.contains(fornecedor)) {
            fornecedor = entityManager.merge(fornecedor);
        }

        entityManager.remove(fornecedor);
        transaction.commit();
    }

    public Fornecedor buscar(Fornecedor fornecedor) {
        return entityManager.find(Fornecedor.class, fornecedor.getId());
    }

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
