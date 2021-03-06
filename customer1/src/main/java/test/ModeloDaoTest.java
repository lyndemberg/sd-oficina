package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.customer1.dao.ModeloDao;
import sd.oficina.shared.model.customer.Modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModeloDaoTest {

    ModeloDao modeloDao;
    Modelo modelo;

    @Before
    public void start() {
        modeloDao = new ModeloDao();
        modelo = new Modelo();
        modelo.setId(100L);
        modelo.setNome("Fiat");
        modelo.setTipo("Carro");
    }

    @Test
    public void test1() {
        assertNotNull(modeloDao.salvar(modelo));
    }

    @Test
    public void test2() {
        assertNotNull(modeloDao.buscarPorId(modelo.getId()));
    }

    @Test
    public void test3() {
        modelo.setNome("Ford");
        assertNotNull(modeloDao.atualizar(modelo));
    }

    @Test
    public void test4() {
        assertNotNull(modeloDao.listarTodos());
    }

    @Test
    public void test5() {
        assertEquals(true, modeloDao.remover(modelo.getId()));
    }
}
