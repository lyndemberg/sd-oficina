package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.order1.dao.OrdemServicoDao;
import sd.oficina.shared.model.order.OrdemServico;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemServicoDaoTest {

    OrdemServicoDao ordemServicoDao;
    OrdemServico ordemServico;

    @Before
    public void start() {
        ordemServicoDao = new OrdemServicoDao();
        ordemServico = new OrdemServico();
        ordemServico.setId(100L);
        ordemServico.setConcluida(false);
        ordemServico.setDataPagamento(LocalDate.now());
        ordemServico.setDataPagamentoJson("14/04/2019");
        ordemServico.setDataRegistro(LocalDate.now());
        ordemServico.setIdCliente(1L);
        ordemServico.setIdVeiculo(1L);
        ordemServico.setPago(true);
    }

    @Test
    public void test1() {
        ordemServicoDao.salvar(ordemServico);
        assertNotNull(ordemServicoDao.buscarPorId(ordemServico.getId()));
    }

    @Test
    public void test2() {
        assertNotNull(ordemServicoDao.buscarPorId(ordemServico.getId()));
    }

    @Test
    public void test3() {
        ordemServico.setConcluida(true);
        assertEquals(ordemServico, ordemServicoDao.atualizar(ordemServico));
    }

    @Test
    public void test4() {
        assertNotNull(ordemServicoDao.listarTodos());
    }

    @Test
    public void test5() {
        assertNotNull(ordemServicoDao.buscarOrdensPorCliente(1L));
    }
}
