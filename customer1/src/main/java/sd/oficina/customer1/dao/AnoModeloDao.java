package sd.oficina.customer1.dao;

import com.google.common.collect.ImmutableList;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.shared.model.AnoModelo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public final class AnoModeloDao implements Serializable {

    private final EntityManager entityManager;

    public AnoModeloDao() {
        this.entityManager = Persistence
                .createEntityManagerFactory("customer1-persistence")
                .createEntityManager();
    }

    public Optional<AnoModelo> salvar(final AnoModelo anoModelo) {
        if (anoModelo == null) throw new TentaPersistirObjetoNullException();

        Optional<AnoModelo> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {
            optional = Optional.ofNullable(this.entityManager.merge(anoModelo));
            this.entityManager.getTransaction().commit();

        } catch (Exception ex) {

            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        } finally {

            this.entityManager.getTransaction().commit();
        }

        return optional;
    }

    public List<AnoModelo> listarTodos() {

        return ImmutableList.copyOf(
                this.entityManager
                        .createQuery("SELECT am FROM AnoModelo am")
                        .getResultList()
        );
    }

    public Optional<AnoModelo> buscarPorId(final Integer id) {

        if (id == null || id <= 0) throw new AtributoIdInvalidoException();

        try {
            return Optional.ofNullable(
                    this.entityManager.find(AnoModelo.class, id)
            );

        } catch (Exception ex) {

            ex.printStackTrace();
            return Optional.empty();
        }

    }

    public Boolean remover(Integer idAnoModelo) {

        if (idAnoModelo == null || idAnoModelo <= 0) throw new AtributoIdInvalidoException();

        AnoModelo anoModelo = null;

        try {
            anoModelo = this.entityManager.find(AnoModelo.class, idAnoModelo);

            if (anoModelo == null) return false;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        this.entityManager.getTransaction().begin();

        try {

            this.entityManager.remove(anoModelo);
        } catch (Exception ex) {

            ex.printStackTrace();

            this.entityManager.getTransaction().rollback();

            return false;
        } finally {
            this.entityManager.getTransaction().commit();
        }

        return true;
    }

    public Optional<AnoModelo> atualizar(final AnoModelo anoModelo) {

        if (anoModelo == null) throw new TentaPersistirObjetoNullException();
        if (anoModelo.getId() <= 0) throw new AtributoIdInvalidoException();

        Optional<AnoModelo> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {

            optional = Optional.ofNullable(this.entityManager.merge(anoModelo));

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
