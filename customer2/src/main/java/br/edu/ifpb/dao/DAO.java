package br.edu.ifpb.dao;

public interface DAO<T> {

    T salvar(T obj);
    T atualizar(T obj);
    void deletar(Object key);
    T buscar(Object key);

}
