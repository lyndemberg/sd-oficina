package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.person2.dao.CidadeDao;
import sd.oficina.shared.model.person.Cidade;

import static org.junit.Assert.*;

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
        assertEquals(cidade, cidadeDao.getById(cidade.getId()));
    }

    @Test
    public void test3() {
        cidade.setNome("Cajazeiras");
        assertEquals(cidade, cidadeDao.atualizar(cidade));
    }

    @Test
    public void test4() {
        assertNotNull(cidadeDao.getAll());
    }

    @Test
    public void test5() {
        cidadeDao.remover(cidade.getId());
        assertNull(cidadeDao.getById(cidade.getId()));
    }
}
