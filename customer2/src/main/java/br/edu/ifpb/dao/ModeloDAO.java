package br.edu.ifpb.dao;

import br.edu.ifpb.model.Modelo;
import br.edu.ifpb.model.Modelo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ModeloDAO implements DAO<Modelo> {

    private EntityManager em;

    public ModeloDAO(){
        this.em = Persistence
                .createEntityManagerFactory("Projeto")
                .createEntityManager();
    }

    public Modelo salvar(Modelo obj){
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
        return obj;
    }

    public Modelo atualizar(Modelo obj) {
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

    public Modelo buscar(Object key) {
        return em.find(Modelo.class, key);
    }
}
