package sd.oficina.customer3.dao;

import java.util.List;

public interface Dao <T> {
    T salvar(T obj);
    T atualizar(T obj);
    void remover(Object id);
    T getById(Object id);
    List<T> getAll();
}
