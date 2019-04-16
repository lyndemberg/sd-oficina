package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.customer2.dao.ModeloDAO;
import sd.oficina.shared.model.customer.Modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModeloDaoTest {

    ModeloDAO modeloDAO;
    Modelo modelo;

    @Before
    public void start() {
        modeloDAO = new ModeloDAO();
        modelo = new Modelo();
        modelo.setId(100L);
        modelo.setNome("Fiat");
        modelo.setTipo("Carro");
    }

    @Test
    public void test1() {
        assertEquals(modelo, modeloDAO.salvar(modelo));
    }

    @Test
    public void test2() {
        assertNotNull(modeloDAO.buscar(modelo.getId()));
    }

    @Test
    public void test3() {
        modelo.setNome("Ford");
        assertNotNull(modeloDAO.atualizar(modelo));
    }

    @Test
    public void test4() {
        assertNotNull(modeloDAO.todos());
    }

    @Test
    public void test5() {
        modeloDAO.deletar(modelo.getId());
        assertNull(modeloDAO.buscar(modelo.getId()));
    }
}
