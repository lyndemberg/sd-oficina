package sd.oficina.person1.daos;

import java.util.List;

public interface Dao<T> {

    void salvar(T object);
    void atualizar(T object);
    void deletar( T object);
    T buscar(T object);
    List<T> listar();

}
