package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.customer1.dao.VeiculoDao;
import sd.oficina.shared.model.customer.Veiculo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertEquals(true, veiculoDao.salvar(veiculo).isPresent());
    }

    @Test
    public void test2() {
        assertEquals(true, veiculoDao.buscarPorId(veiculo.getId()).isPresent());
    }

    @Test
    public void test3() {
        veiculo.setQuilometragem(80000);
        assertEquals(true, veiculoDao.atualizar(veiculo).isPresent());
    }

    @Test
    public void test4() {
        assertNotNull(veiculoDao.listarTodos());
    }

    @Test
    public void test5() {
        assertEquals(true, veiculoDao.remover(veiculo.getId()));
    }
}
