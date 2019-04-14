package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.person1.daos.EstadoDao;
import sd.oficina.shared.model.person.Estado;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EstadoDaoTest {

    EstadoDao estadoDao;
    Estado estado;

    @Before
    public void start() {
        estadoDao = new EstadoDao();
        estado = new Estado();
        estado.setId(100L);
        estado.setNome("Paraíba");
    }

    @Test
    public void test1() {
        assertEquals(estado, estadoDao.salvar(estado));
    }

    @Test
    public void test2() {
        assertEquals(estado, estadoDao.buscar(estado));
    }

    @Test
    public void test3() {
        estado.setNome("Ceará");
        assertEquals(estado, estadoDao.atualizar(estado));
    }

    @Test
    public void test4() {
        assertNotNull(estadoDao.listar());
    }

    @Test
    public void test5() {
        estadoDao.deletar(estado);
        assertNull(estadoDao.buscar(estado));
    }
}
