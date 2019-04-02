package sd.oficina.store1.dao;

import java.util.List;

public interface DAO<T> {

    T salvar(T obj);

    T atualizar(T obj);

    void deletar(Object key);

    T buscar(Object key);

    List<T> buscarTodos();
}
