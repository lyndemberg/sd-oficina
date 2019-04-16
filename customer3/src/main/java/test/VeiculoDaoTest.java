package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.customer3.dao.VeiculoDao;
import sd.oficina.shared.model.customer.Veiculo;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VeiculoDaoTest {

    VeiculoDao veiculoDao;
    Veiculo veiculo;

    @Before
    public void start() {
        veiculoDao = new VeiculoDao();
        veiculo = new Veiculo();
        veiculo.setId(100L);
        veiculo.setPlaca("ABC 5060");
        veiculo.setQuilometragem(30000);
    }

    @Test
    public void test1() {
        assertEquals(veiculo, veiculoDao.salvar(veiculo));
    }

    @Test
    public void test2() {
        assertNotNull(veiculoDao.getById(veiculo.getId()));
    }

    @Test
    public void test3() {
        veiculo.setQuilometragem(80000);
        assertNotNull(veiculoDao.atualizar(veiculo));
    }

    @Test
    public void test4() {
        assertNotNull(veiculoDao.getAll());
    }

    @Test
    public void test5() {
        veiculoDao.remover(veiculo.getId());
        assertNull(veiculoDao.getById(veiculo.getId()));
    }
}
