package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Nota;
import sd.oficina.store2.daos.NotaDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaDAOTest {

    NotaDao notaDao;
    Nota nota;

    @Before
    public void start() {
        notaDao = new NotaDao();
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
        assertEquals(nota, notaDao.salvar(nota));
    }

    @Test
    public void test2() {
        assertEquals(nota, notaDao.buscar(nota));
    }

    @Test
    public void test3() {
        nota.setNumero(30);
        assertEquals(nota, notaDao.atualizar(nota));
    }

    @Test
    public void test4() {
        assertNotNull(notaDao.listar());
    }

    @Test
    public void test5() {
        notaDao.deletar(nota);
        assertNull(notaDao.buscar(nota));
    }
}
