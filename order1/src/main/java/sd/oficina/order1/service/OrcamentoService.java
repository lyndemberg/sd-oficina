package sd.oficina.order1.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sd.oficina.order1.exception.ErroEmissaoOrcamento;
import sd.oficina.shared.proto.order.Item;
import sd.oficina.shared.proto.order.OrcamentoData;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrcamentoService {


    public byte[] gerarRelatorioOrcamento(OrcamentoData orcamentoData) throws ErroEmissaoOrcamento {
        byte[] pdfBytes = new byte[1];
        Map<String,Object> parametros = new HashMap<>();
        parametros.put("nomeInteressado",orcamentoData.getNomeInteressado());
        parametros.put("cpfInteressado", orcamentoData.getCpfInteressado());
        Double total = orcamentoData.getItensList()
                .stream()
                .collect(Collectors.summingDouble(Item::getValor));
        parametros.put("total",total);
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport("orcamento.jasper",
                    parametros, new JRBeanCollectionDataSource(orcamentoData.getItensList()));
            pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ErroEmissaoOrcamento();
        }
        return pdfBytes;
    }
}
