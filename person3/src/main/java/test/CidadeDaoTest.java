package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.person3.daos.CidadeDao;
import sd.oficina.shared.model.person.Cidade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CidadeDaoTest {

    CidadeDao cidadeDao;
    Cidade cidade;

    @Before
    public void start() {
        cidadeDao = new CidadeDao();
        cidade = new Cidade();
        cidade.setId(10000L);
        cidade.setNome("Cachoeira dos índios");
    }

    @Test
    public void test1() {
        assertEquals(cidade, cidadeDao.salvar(cidade));
    }

    @Test
    public void test2() {
        assertEquals(cidade, cidadeDao.buscar(cidade));
    }

    @Test
    public void test3() {
        cidade.setNome("Cajazeiras");
        assertEquals(cidade, cidadeDao.atualizar(cidade));
    }

    @Test
    public void test4() {
        assertNotNull(cidadeDao.listar());
    }

    @Test
    public void test5() {
        cidadeDao.deletar(cidade);
        assertNull(cidadeDao.buscar(cidade));
    }
}
