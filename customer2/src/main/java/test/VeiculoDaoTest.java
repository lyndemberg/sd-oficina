package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.customer2.dao.VeiculoDAO;
import sd.oficina.shared.model.customer.Veiculo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VeiculoDaoTest {

    VeiculoDAO veiculoDAO;
    Veiculo veiculo;

    @Before
    public void start() {
        veiculoDAO = new VeiculoDAO();
        veiculo = new Veiculo();
        veiculo.setId(100L);
        veiculo.setPlaca("ABC 5060");
        veiculo.setQuilometragem(30000);
    }

    @Test
    public void test1() {
        assertEquals(veiculo, veiculoDAO.salvar(veiculo));
    }

    @Test
    public void test2() {
        assertNotNull(veiculoDAO.buscar(veiculo.getId()));
    }

    @Test
    public void test3() {
        veiculo.setQuilometragem(80000);
        assertNotNull(veiculoDAO.atualizar(veiculo));
    }

    @Test
    public void test4() {
        assertNotNull(veiculoDAO.todos());
    }

    @Test
    public void test5() {
        veiculoDAO.deletar(veiculo.getId());
        assertNull(veiculoDAO.buscar(veiculo.getId()));
    }
}
