package sd.oficina.order1.grpc;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import sd.oficina.order1.exception.ErroEmissaoOrcamento;
import sd.oficina.order1.service.OrcamentoService;
import sd.oficina.shared.proto.order.OrcamentoData;
import sd.oficina.shared.proto.order.OrcamentoServiceGrpc;
import sd.oficina.shared.proto.order.ResultOrcamento;

public class OrcamentoServiceImpl extends OrcamentoServiceGrpc.OrcamentoServiceImplBase {

    private final OrcamentoService orcamentoService;

    public OrcamentoServiceImpl() {
        orcamentoService = new OrcamentoService();
    }

    @Override
    public void fazerOrcamento(OrcamentoData request, StreamObserver<ResultOrcamento> responseObserver) {
        try {
            byte[] bytes = orcamentoService.gerarRelatorioOrcamento(request);
            ResultOrcamento result = ResultOrcamento.newBuilder().setFile(ByteString.copyFrom(bytes)).build();
            responseObserver.onNext(result);
            responseObserver.onCompleted();
        } catch (ErroEmissaoOrcamento erroEmissaoOrcamento) {
            responseObserver.onError(erroEmissaoOrcamento);
            erroEmissaoOrcamento.printStackTrace();
        }
    }
}
