package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.customer1.dao.AnoModeloDao;
import sd.oficina.shared.model.customer.AnoModelo;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnoModeloDaoTest {

    AnoModeloDao anoModeloDao;
    AnoModelo anoModelo;

    @Before
    public void start() {
        anoModeloDao = new AnoModeloDao();
        anoModelo = new AnoModelo();
        anoModelo.setId(100L);
        anoModelo.setNome("Fiat 2019");
        anoModelo.setTipo("Carro");
        anoModelo.setValor(100000);
    }

    @Test
    public void test1() {
       assertEquals(true, anoModeloDao.salvar(anoModelo).isPresent());
    }

    @Test
    public void test2() {
        assertEquals(true, anoModeloDao.buscarPorId(anoModelo.getId()).isPresent());
    }

    @Test
    public void test3() {
        anoModelo.setNome("Fiat 2017");
        assertEquals(true, anoModeloDao.atualizar(anoModelo).isPresent());
    }

    @Test
    public void test4() {
        assertNotNull(anoModeloDao.listarTodos());
    }

    @Test
    public void test5() {
        assertEquals(true, anoModeloDao.remover(anoModelo.getId()));
    }
}
