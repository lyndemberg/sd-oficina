package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.customer3.dao.FabricanteDao;
import sd.oficina.shared.model.customer.Fabricante;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FabricanteDaoTest {

    FabricanteDao fabricanteDao;
    Fabricante fabricante;

    @Before
    public void start() {
        fabricanteDao = new FabricanteDao();
        fabricante = new Fabricante();
        fabricante.setId(100L);
        fabricante.setNome("Fiat");
    }

    @Test
    public void test1() {
        assertEquals(fabricante, fabricanteDao.salvar(fabricante));
    }

    @Test
    public void test2() {
        assertNotNull(fabricanteDao.getById(fabricante.getId()));
    }

    @Test
    public void test3() {
        fabricante.setNome("Ford");
        assertNotNull(fabricanteDao.atualizar(fabricante));
    }

    @Test
    public void test4() {
        assertNotNull(fabricanteDao.getAll());
    }

    @Test
    public void test5() {
        fabricanteDao.remover(fabricante.getId());
        assertNull(fabricanteDao.getById(fabricante.getId()));
    }
}
