package test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sd.oficina.order2.dao.OrdemServicoDao;
import sd.oficina.shared.model.order.OrdemServico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        ordemServico.setId(200L);
        ordemServico.setConcluida(false);
        ordemServico.setDataPagamento(LocalDate.of(2019, 04, 15));
        ordemServico.setDataPagamentoJson("15/04/2019");
        ordemServico.setDataRegistro(LocalDate.now());
        ordemServico.setIdCliente(1L);
        ordemServico.setIdVeiculo(1L);
        ordemServico.setPago(true);
        List<Long> servicos = new ArrayList<>();
        servicos.add(1L);
        servicos.add(2L);
        ordemServico.setServicos(servicos);
    }

    @Test
    public void test1() {
        assertEquals(Optional.of(ordemServico), ordemServicoDao.salvar(ordemServico));
    }

    @Test
    public void test2() {
        assertNotNull(ordemServicoDao.buscarPorId(ordemServico.getId()));
    }

    @Test
    public void test3() {
        ordemServico.setConcluida(true);
        assertEquals(Optional.of(ordemServico), ordemServicoDao.atualizar(ordemServico));
    }

    @Test
    public void test4() {
        assertNotNull(ordemServicoDao.listarTodos());
    }

    @Test
    public void test5() {
        assertNotNull(ordemServicoDao.buscarPorClienteId(1L));
    }

    @Test
    public void test6() {
        assertEquals(true, ordemServicoDao.remover(ordemServico.getId()));
    }
}
