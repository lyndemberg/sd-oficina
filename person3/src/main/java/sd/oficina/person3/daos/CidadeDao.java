package sd.oficina.person3.daos;

import sd.oficina.shared.model.person.Cidade;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CidadeDao {

    private EntityManager entityManager =
                Persistence.createEntityManagerFactory("persistencia")
                        .createEntityManager();

    public Cidade salvar(Cidade cidade) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Cidade cidadeSalva = entityManager.merge(cidade);
        transaction.commit();

        return cidadeSalva;
    }

    public Cidade atualizar(Cidade cidade) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(cidade);
        transaction.commit();
        return cidade;
    }

    public void deletar(Cidade cidade) {

        cidade.setEstado(null);
        cidade = atualizar(cidade);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (!entityManager.contains(cidade)) {
            cidade = entityManager.merge(cidade);
        }

        entityManager.remove(cidade);
        transaction.commit();
    }

    public Cidade buscar(Cidade cidade) {
        return entityManager.find(Cidade.class, cidade.getId());
    }

    public List<Cidade> listar() {
        TypedQuery<Cidade> query = entityManager
                .createQuery("SELECT cidade FROM Cidade cidade", Cidade.class);
        List<Cidade> cidades = query.getResultList();
        if (cidades == null) {
            return new ArrayList<>();
        }
        List<Cidade> cidadesRecuperadas= new ArrayList<>();
        for (Cidade cidade : cidades) {
            cidadesRecuperadas.add(cidade);

        }
        return cidadesRecuperadas;
    }
}
