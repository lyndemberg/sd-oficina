package sd.oficina.customer1.dao;

import com.google.common.collect.ImmutableList;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.shared.model.customer.Modelo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public final class ModeloDao implements Serializable {

    private final EntityManager entityManager;

    public ModeloDao() {
        this.entityManager = Persistence
                .createEntityManagerFactory("customer1-persistence")
                .createEntityManager();
    }

    public Optional<Modelo> salvar(final Modelo modelo) {
        if (modelo == null) throw new TentaPersistirObjetoNullException();

        Optional<Modelo> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {
            optional = Optional.ofNullable(this.entityManager.merge(modelo));
            this.entityManager.getTransaction().commit();

        } catch (Exception ex) {

            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        } finally {

            this.entityManager.getTransaction().commit();
        }

        return optional;
    }

    public List<Modelo> listarTodos() {

        return ImmutableList.copyOf(
                this.entityManager
                        .createQuery("SELECT f FROM Modelo f")
                        .getResultList()
        );
    }

    public Optional<Modelo> buscarPorId(final Integer id) {

        if (id == null || id <= 0) throw new AtributoIdInvalidoException();

        try {
            return Optional.ofNullable(
                    this.entityManager.find(Modelo.class, id)
            );

        } catch (Exception ex) {

            ex.printStackTrace();
            return Optional.empty();
        }

    }

    public Boolean remover(Integer idModelo) {

        if (idModelo == null || idModelo <= 0) throw new AtributoIdInvalidoException();

        Modelo modelo = null;

        try {
            modelo = this.entityManager.find(Modelo.class, idModelo);

            if (modelo == null) return false;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        this.entityManager.getTransaction().begin();

        try {

            this.entityManager.remove(modelo);
        } catch (Exception ex) {

            ex.printStackTrace();

            this.entityManager.getTransaction().rollback();

            return false;
        } finally {
            this.entityManager.getTransaction().commit();
        }

        return true;
    }

    public Optional<Modelo> atualizar(final Modelo modelo) {

        if (modelo == null) throw new TentaPersistirObjetoNullException();
        if (modelo.getId() <= 0) throw new AtributoIdInvalidoException();

        Optional<Modelo> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {

            optional = Optional.ofNullable(this.entityManager.merge(modelo));

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
