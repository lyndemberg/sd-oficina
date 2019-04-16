package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.customer2.dao.FabricanteDAO;
import sd.oficina.shared.model.customer.Fabricante;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FabricanteDaoTest {

    FabricanteDAO fabricanteDAO;
    Fabricante fabricante;

    @Before
    public void start() {
        fabricanteDAO = new FabricanteDAO();
        fabricante = new Fabricante();
        fabricante.setId(100L);
        fabricante.setNome("Fiat");
    }

    @Test
    public void test1() {
        assertEquals(fabricante, fabricanteDAO.salvar(fabricante));
    }

    @Test
    public void test2() {
        assertNotNull(fabricanteDAO.buscar(fabricante.getId()));
    }

    @Test
    public void test3() {
        fabricante.setNome("Ford");
        assertNotNull(fabricanteDAO.atualizar(fabricante));
    }

    @Test
    public void test4() {
        assertNotNull(fabricanteDAO.todos());
    }

    @Test
    public void test5() {
        fabricanteDAO.deletar(fabricante.getId());
        assertNull(fabricanteDAO.buscar(fabricante.getId()));
    }
}
