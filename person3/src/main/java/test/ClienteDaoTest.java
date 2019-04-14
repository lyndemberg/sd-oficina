package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.person3.daos.ClienteDao;
import sd.oficina.shared.model.person.Cliente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteDaoTest {

    ClienteDao clienteDao;
    Cliente cliente;

    @Before
    public void start() {
        clienteDao = new ClienteDao();
        cliente = new Cliente();
        cliente.setId(100L);
        cliente.setNome("João da Silva");
        cliente.setBairro("Zona Rural");
        cliente.setCEP("99999-000");
        cliente.setComplemento("Sítio");
        cliente.setCPF("111.111.111-11");
        cliente.setEmail("joao@gmail.com");
        cliente.setLogradouro("Casa");
        cliente.setNumero(10);
        cliente.setTelefoneCelular("(83) 99999-9999");
        cliente.setTelefoneFixo("(83) 9999-9999");
    }

    @Test
    public void test1() {
        assertEquals(cliente, clienteDao.salvar(cliente));
    }

    @Test
    public void test2() {
        assertEquals(cliente, clienteDao.buscar(cliente));
    }

    @Test
    public void test3() {
        cliente.setNome("Pedro de Sousa");
        assertEquals(cliente, clienteDao.atualizar(cliente));
    }

    @Test
    public void test4() {
        assertNotNull(clienteDao.listar());
    }

    @Test
    public void test5() {
        clienteDao.deletar(cliente);
        assertNull(clienteDao.buscar(cliente));
    }
}
