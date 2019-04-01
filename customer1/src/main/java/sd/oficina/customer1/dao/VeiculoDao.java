package sd.oficina.customer1.dao;

import com.google.common.collect.ImmutableList;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.shared.model.customer.Veiculo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public final class VeiculoDao implements Serializable {

    private final EntityManager entityManager;

    public VeiculoDao() {
        this.entityManager = Persistence
                .createEntityManagerFactory("customer1-persistence")
                .createEntityManager();
    }

    public Optional<Veiculo> salvar(final Veiculo veiculo) {
        if (veiculo == null) throw new TentaPersistirObjetoNullException();

        Optional<Veiculo> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {
            optional = Optional.ofNullable(this.entityManager.merge(veiculo));
            this.entityManager.getTransaction().commit();

        } catch (Exception ex) {

            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        } finally {

            this.entityManager.getTransaction().commit();
        }

        return optional;
    }

    public List<Veiculo> listarTodos() {

        return ImmutableList.copyOf(
                this.entityManager
                        .createQuery("SELECT f FROM Veiculo f")
                        .getResultList()
        );
    }

    public Optional<Veiculo> buscarPorId(final Integer id) {

        if (id == null || id <= 0) throw new AtributoIdInvalidoException();

        try {
            return Optional.ofNullable(
                    this.entityManager.find(Veiculo.class, id)
            );

        } catch (Exception ex) {

            ex.printStackTrace();
            return Optional.empty();
        }

    }

    public Boolean remover(Integer idVeiculo) {

        if (idVeiculo == null || idVeiculo <= 0) throw new AtributoIdInvalidoException();

        Veiculo veiculo = null;

        try {
            veiculo = this.entityManager.find(Veiculo.class, idVeiculo);

            if (veiculo == null) return false;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        this.entityManager.getTransaction().begin();

        try {

            this.entityManager.remove(veiculo);
        } catch (Exception ex) {

            ex.printStackTrace();

            this.entityManager.getTransaction().rollback();

            return false;
        } finally {
            this.entityManager.getTransaction().commit();
        }

        return true;
    }

    public Optional<Veiculo> atualizar(final Veiculo veiculo) {

        if (veiculo == null) throw new TentaPersistirObjetoNullException();
        if (veiculo.getId() <= 0) throw new AtributoIdInvalidoException();

        Optional<Veiculo> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {

            optional = Optional.ofNullable(this.entityManager.merge(veiculo));

        } catch (Exception ex) {

            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();

            return Optional.empty();
        } finally {
            this.entityManager.getTransaction().commit();
        }

        return optional;
    }

}
