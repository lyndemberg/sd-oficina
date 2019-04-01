package sd.oficina.customer1.dao;

import com.google.common.collect.ImmutableList;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.shared.model.Fabricante;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public final class FabricanteDao implements Serializable {

    private final EntityManager entityManager;

    public FabricanteDao() {
        this.entityManager = Persistence
                .createEntityManagerFactory("customer1-persistence")
                .createEntityManager();
    }

    public Optional<Fabricante> salvar(final Fabricante fabricante) {
        if (fabricante == null) throw new TentaPersistirObjetoNullException();

        Optional<Fabricante> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {
            optional = Optional.ofNullable(this.entityManager.merge(fabricante));
            this.entityManager.getTransaction().commit();

        } catch (Exception ex) {

            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        } finally {

            this.entityManager.getTransaction().commit();
        }

        return optional;
    }

    public List<Fabricante> listarTodos() {

        return ImmutableList.copyOf(
                this.entityManager
                        .createQuery("SELECT f FROM Fabricante f")
                        .getResultList()
        );
    }

    public Optional<Fabricante> buscarPorId(final Integer id) {

        if (id == null || id <= 0) throw new AtributoIdInvalidoException();

        try {
            return Optional.ofNullable(
                    this.entityManager.find(Fabricante.class, id)
            );

        } catch (Exception ex) {

            ex.printStackTrace();
            return Optional.empty();
        }

    }

    public Boolean remover(Integer idFabricante) {

        if (idFabricante == null || idFabricante <= 0) throw new AtributoIdInvalidoException();

        Fabricante fabricante = null;

        try {
            fabricante = this.entityManager.find(Fabricante.class, idFabricante);

            if (fabricante == null) return false;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        this.entityManager.getTransaction().begin();

        try {

            this.entityManager.remove(fabricante);
        } catch (Exception ex) {

            ex.printStackTrace();

            this.entityManager.getTransaction().rollback();

            return false;
        } finally {
            this.entityManager.getTransaction().commit();
        }

        return true;
    }

    public Optional<Fabricante> atualizar(final Fabricante fabricante) {

        if (fabricante == null) throw new TentaPersistirObjetoNullException();
        if (fabricante.getId() <= 0) throw new AtributoIdInvalidoException();

        Optional<Fabricante> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {

            optional = Optional.ofNullable(this.entityManager.merge(fabricante));

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
