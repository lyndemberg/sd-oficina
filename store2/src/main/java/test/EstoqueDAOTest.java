package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.store2.daos.EstoqueDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EstoqueDAOTest {

    EstoqueDao estoqueDao;
    Estoque estoque;

    @Before
    public void start() {
        estoqueDao = new EstoqueDao();
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
        assertEquals(estoque, estoqueDao.salvar(estoque));
    }

    @Test
    public void test2() {
        assertEquals(estoque, estoqueDao.buscar(estoque));
    }

    @Test
    public void test3() {
        estoque.setNomePeca("Eixo");
        assertEquals(estoque, estoqueDao.atualizar(estoque));
    }

    @Test
    public void test4() {
        assertNotNull(estoqueDao.listar());
    }

    @Test
    public void test5() {
        estoqueDao.deletar(estoque);
        assertNull(estoqueDao.buscar(estoque));
    }
}
