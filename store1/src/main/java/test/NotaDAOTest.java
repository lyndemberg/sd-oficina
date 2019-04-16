package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Nota;
import sd.oficina.store1.dao.NotaDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaDAOTest {

    NotaDAO notaDAO;
    Nota nota;

    @Before
    public void start() {
        notaDAO = new NotaDAO();
        nota = new Nota();
        nota.setId(100L);
        nota.setIdFornecedor(1);
        nota.setDataCompra(LocalDate.now());
        nota.setDataVencimento(LocalDate.now());
        nota.setNumero(20);
        List<Estoque> estoques = new ArrayList<>();
        nota.setEstoques(estoques);
    }

    @Test
    public void test1() {
        assertEquals(nota, notaDAO.salvar(nota));
    }

    @Test
    public void test2() {
        assertEquals(nota, notaDAO.buscar(nota.getId()));
    }

    @Test
    public void test3() {
        nota.setNumero(30);
        assertEquals(nota, notaDAO.atualizar(nota));
    }

    @Test
    public void test4() {
        assertNotNull(notaDAO.buscarTodos());
    }

    @Test
    public void test5() {
        notaDAO.deletar(nota.getId());
        assertNull(notaDAO.buscar(nota.getId()));
    }
}
