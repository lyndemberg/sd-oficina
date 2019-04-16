package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.store2.daos.ServicoDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServicoDAOTest {

    ServicoDao servicoDao;
    Servico servico;

    @Before
    public void start() {
        servicoDao = new ServicoDao();
        servico = new Servico();
        servico.setId(100L);
        servico.setDescricao("Troca de Óleo");
        servico.setValor(50);
        List<Estoque> estoques = new ArrayList<>();
        servico.setEstoques(estoques);
    }

    @Test
    public void test1() {
        assertEquals(servico, servicoDao.salvar(servico));
    }

    @Test
    public void test2() {
        assertEquals(servico, servicoDao.buscar(servico));
    }

    @Test
    public void test3() {
        servico.setValor(100);
        assertEquals(servico, servicoDao.atualizar(servico));
    }

    @Test
    public void test4() {
        assertNotNull(servicoDao.listar());
    }

    @Test
    public void test5() {
        servicoDao.deletar(servico);
        assertNull(servicoDao.buscar(servico));
    }
}
