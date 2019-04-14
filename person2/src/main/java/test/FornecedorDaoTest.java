package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.person2.dao.FornecedorDao;
import sd.oficina.shared.model.person.Fornecedor;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FornecedorDaoTest {

    FornecedorDao fornecedorDao;
    Fornecedor fornecedor;

    @Before
    public void start() {
        fornecedorDao = new FornecedorDao();
        fornecedor = new Fornecedor();
        fornecedor.setId(100L);
        fornecedor.setVendedor("João da Silva");
        fornecedor.setBairro("Zona Urbana");
        fornecedor.setCEP("99999-000");
        fornecedor.setComplemento("Centro");
        fornecedor.setCNPJ("11.111.111/1111-11");
        fornecedor.setRazaoSocial("Peças");
        fornecedor.setLogradouro("Loja");
        fornecedor.setNumero(15);
        fornecedor.setTelefone("(83) 9999-9999");
        fornecedor.setCodigo(250);
        fornecedor.setNomeFantasia("Auto Peças");
    }

    @Test
    public void test1() {
        assertEquals(fornecedor, fornecedorDao.salvar(fornecedor));
    }

    @Test
    public void test2() {
        assertEquals(fornecedor, fornecedorDao.getById(fornecedor.getId()));
    }

    @Test
    public void test3() {
        fornecedor.setVendedor("Pedro de Sousa");
        assertEquals(fornecedor, fornecedorDao.atualizar(fornecedor));
    }

    @Test
    public void test4() {
        assertNotNull(fornecedorDao.getAll());
    }

    @Test
    public void test5() {
        fornecedorDao.remover(fornecedor.getId());
        assertNull(fornecedorDao.getById(fornecedor.getId()));
    }
}
