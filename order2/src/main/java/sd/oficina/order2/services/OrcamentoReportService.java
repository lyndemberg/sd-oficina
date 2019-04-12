package sd.oficina.order2.services;

import com.google.common.collect.ImmutableMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sd.oficina.shared.proto.order.Item;
import sd.oficina.shared.proto.order.OrcamentoData;

import java.util.Map;

public class OrcamentoReportService {

    public byte[] gerarRelatorioOrcamento(OrcamentoData orcamentoData) throws JRException {
        byte[] pdfBytes;

        Map<String, Object> parametros = ImmutableMap.of(
                "nomeInteressado", orcamentoData.getNomeInteressado(),
                "cpfInteressado", orcamentoData.getCpfInteressado(),
                "total", orcamentoData
                        .getItensList()
                        .stream()
                        .mapToDouble(Item::getValor)
                        .sum()
        );

        JasperPrint jasperPrint = JasperFillManager.fillReport("orcamento.jasper",
                parametros, new JRBeanCollectionDataSource(orcamentoData.getItensList()));
        pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return pdfBytes;
    }

}
