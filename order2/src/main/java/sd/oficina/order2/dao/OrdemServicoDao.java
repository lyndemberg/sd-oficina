package sd.oficina.order2.dao;

import com.google.common.collect.ImmutableList;
import sd.oficina.order2.exceptions.AtributoIdInvalidoException;
import sd.oficina.order2.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.shared.model.order.OrdemServico;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class OrdemServicoDao {

    private final EntityManager entityManager;

    public OrdemServicoDao() {
        this.entityManager = Persistence
                .createEntityManagerFactory("order2-persistence")
                .createEntityManager();
    }

    public Optional<OrdemServico> salvar(final OrdemServico ordemServico) {

        if (ordemServico == null) throw new TentaPersistirObjetoNullException();

        Optional<OrdemServico> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {
            optional = Optional.ofNullable(this.entityManager.merge(ordemServico));
            this.entityManager.getTransaction().commit();

        } catch (Exception ex) {

            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();
        } finally {

            this.entityManager.getTransaction().commit();
        }

        return optional;
    }

    public Boolean remover(Long idOrdemServico) {

        if (idOrdemServico == null || idOrdemServico <= 0) throw new AtributoIdInvalidoException();

        OrdemServico ordemServico = null;

        try {
            ordemServico = this.entityManager.find(OrdemServico.class, idOrdemServico);

            if (ordemServico == null) return false;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        this.entityManager.getTransaction().begin();

        try {

            this.entityManager.remove(ordemServico);
        } catch (Exception ex) {

            ex.printStackTrace();

            this.entityManager.getTransaction().rollback();

            return false;
        } finally {
            this.entityManager.getTransaction().commit();
        }

        return true;
    }

    public Optional<OrdemServico> atualizar(final OrdemServico ordemServico) {

        if (ordemServico == null) throw new TentaPersistirObjetoNullException();
        if (ordemServico.getId() <= 0) throw new AtributoIdInvalidoException();

        Optional<OrdemServico> optional = Optional.empty();

        this.entityManager.getTransaction().begin();

        try {

            optional = Optional.ofNullable(this.entityManager.merge(ordemServico));

        } catch (Exception ex) {

            ex.printStackTrace();
            this.entityManager.getTransaction().rollback();

            return Optional.empty();
        } finally {
            this.entityManager.getTransaction().commit();
        }

        return optional;
    }

    public List<OrdemServico> listarTodos() {

        return ImmutableList.copyOf(
                this.entityManager
                        .createQuery("SELECT os FROM OrdemServico os")
                        .getResultList()
        );
    }

    public Optional<OrdemServico> buscarPorId(final Long id) {

        if (id == null || id <= 0) throw new AtributoIdInvalidoException();

        try {
            return Optional.ofNullable(
                    this.entityManager.find(OrdemServico.class, id)
            );

        } catch (Exception ex) {

            ex.printStackTrace();
            return Optional.empty();
        }

    }

    public List<OrdemServico> buscarPorClienteId(final Long clienteId){
        TypedQuery<OrdemServico> query = entityManager
                .createQuery("SELECT os FROM OrdemServico os WHERE os.idCliente = :clienteId", OrdemServico.class);

        query.setParameter("clienteId", clienteId);

        return ImmutableList.copyOf(query.getResultList());
    }

}
