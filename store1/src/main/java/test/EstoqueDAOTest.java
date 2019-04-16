package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.store1.dao.EstoqueDAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EstoqueDAOTest {

    EstoqueDAO estoqueDAO;
    Estoque estoque;

    @Before
    public void start() {
        estoqueDAO = new EstoqueDAO();
        estoque = new Estoque();
        estoque.setIdPeca(100L);
        estoque.setCodigoPeca(1000);
        estoque.setNomePeca("Motor");
        estoque.setQtdPeca(10);
        estoque.setValidade("14/04/2010");
        estoque.setValorPeca(5000);
    }

    @Test
    public void test1() {
        assertEquals(estoque, estoqueDAO.salvar(estoque));
    }

    @Test
    public void test2() {
        assertEquals(estoque, estoqueDAO.buscar(estoque.getIdPeca()));
    }

    @Test
    public void test3() {
        estoque.setNomePeca("Eixo");
        assertEquals(estoque, estoqueDAO.atualizar(estoque));
    }

    @Test
    public void test4() {
        assertNotNull(estoqueDAO.buscarTodos());
    }

    @Test
    public void test5() {
        estoqueDAO.deletar(estoque.getIdPeca());
        assertNull(estoqueDAO.buscar(estoque.getIdPeca()));
    }
}
