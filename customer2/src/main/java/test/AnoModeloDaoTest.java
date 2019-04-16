package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.customer2.dao.AnoModeloDAO;
import sd.oficina.shared.model.customer.AnoModelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnoModeloDaoTest {

    AnoModeloDAO anoModeloDAO;
    AnoModelo anoModelo;

    @Before
    public void start() {
        anoModeloDAO = new AnoModeloDAO();
        anoModelo = new AnoModelo();
        anoModelo.setId(100L);
        anoModelo.setNome("Fiat 2019");
        anoModelo.setTipo("Carro");
        anoModelo.setValor(100000);
    }

    @Test
    public void test1() {
       assertEquals(anoModelo, anoModeloDAO.salvar(anoModelo));
    }

    @Test
    public void test2() {
        assertNotNull(anoModeloDAO.buscar(anoModelo.getId()));
    }

    @Test
    public void test3() {
        anoModelo.setNome("Fiat 2017");
        assertNotNull(anoModeloDAO.atualizar(anoModelo));
    }

    @Test
    public void test4() {
        assertNotNull(anoModeloDAO.todos());
    }

    @Test
    public void test5() {
        anoModeloDAO.deletar(anoModelo.getId());
        assertNull(anoModeloDAO.buscar(anoModelo.getId()));
    }
}
