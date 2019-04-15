package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.store1.dao.ServicoDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServicoDAOTest {

    ServicoDAO servicoDAO;
    Servico servico;

    @Before
    public void start() {
        servicoDAO = new ServicoDAO();
        servico = new Servico();
        servico.setId(100L);
        servico.setDescricao("Troca de Óleo");
        servico.setValor(50);
        List<Estoque> estoques = new ArrayList<>();
        servico.setEstoques(estoques);
    }

    @Test
    public void test1() {
        assertEquals(servico, servicoDAO.salvar(servico));
    }

    @Test
    public void test2() {
        assertEquals(servico, servicoDAO.buscar(servico.getId()));
    }

    @Test
    public void test3() {
        servico.setValor(100);
        assertEquals(servico, servicoDAO.atualizar(servico));
    }

    @Test
    public void test4() {
        assertNotNull(servicoDAO.buscarTodos());
    }

    @Test
    public void test5() {
        servicoDAO.deletar(servico.getId());
        assertNull(servicoDAO.buscar(servico.getId()));
    }
}
