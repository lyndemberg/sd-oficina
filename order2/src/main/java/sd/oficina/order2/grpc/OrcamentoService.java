package sd.oficina.order2.grpc;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import net.sf.jasperreports.engine.JRException;
import sd.oficina.order2.services.OrcamentoReportService;
import sd.oficina.shared.proto.order.OrcamentoData;
import sd.oficina.shared.proto.order.OrcamentoServiceGrpc;
import sd.oficina.shared.proto.order.ResultOrcamento;

public class OrcamentoService extends OrcamentoServiceGrpc.OrcamentoServiceImplBase {

    private final OrcamentoReportService reportService;

    public OrcamentoService() {
        this.reportService = new OrcamentoReportService();
    }

    @Override
    public void fazerOrcamento(OrcamentoData request, StreamObserver<ResultOrcamento> responseObserver) {
        try {

            byte[] bytes = reportService.gerarRelatorioOrcamento(request);

            ResultOrcamento result = ResultOrcamento.newBuilder().setFile(ByteString.copyFrom(bytes)).build();

            responseObserver.onNext(result);
            responseObserver.onCompleted();

        } catch (JRException jrEx) {

            jrEx.printStackTrace();
            responseObserver.onError(jrEx);
        }
    }

}
