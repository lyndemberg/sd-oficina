package br.edu.ifpb.dao;

import br.edu.ifpb.model.Fabricante;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class FabricanteDAO implements DAO<Fabricante> {

    private EntityManager em;

    public FabricanteDAO(){
        this.em = Persistence
                .createEntityManagerFactory("Projeto")
                .createEntityManager();
    }

    public Fabricante salvar(Fabricante obj){
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        return obj;
    }

    public Fabricante atualizar(Fabricante obj) {
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

    public Fabricante buscar(Object key) {
        return em.find(Fabricante.class, key);
    }

    public List<Fabricante> todos(){
        return em.createQuery("SELECT f FROM Fabricante  f").getResultList();
    }
}
