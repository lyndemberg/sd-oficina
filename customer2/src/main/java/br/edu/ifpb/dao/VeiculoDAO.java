package br.edu.ifpb.dao;

import br.edu.ifpb.model.Veiculo;
import br.edu.ifpb.model.Veiculo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class VeiculoDAO implements DAO<Veiculo> {
    private EntityManager em;

    public VeiculoDAO(){
        this.em = Persistence
                .createEntityManagerFactory("Projeto")
                .createEntityManager();
    }

    public Veiculo salvar(Veiculo obj){
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        return obj;
    }

    public Veiculo atualizar(Veiculo obj) {
        em.getTransaction().begin();
        em.merge(obj);
        em.getTransaction().commit();
        return buscar(obj.getId());
    }

    public void deletar(Object key) {
        em.getTransaction().begin();
        em.remove(buscar(key));
        em.getTransaction().commit();
    }

    public Veiculo buscar(Object key) {
        return em.find(Veiculo.class, key);
    }

    public List<Veiculo> todos(){
        return em.createQuery("SELECT v FROM Veiculo  v").getResultList();
    }
}
