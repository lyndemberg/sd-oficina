package sd.oficina.order1.grpc;

import io.grpc.stub.StreamObserver;
import sd.oficina.shared.proto.order.OrcamentoData;
import sd.oficina.shared.proto.order.OrcamentoServiceGrpc;
import sd.oficina.shared.proto.order.ResultOrcamento;

public class OrcamentoServiceImpl extends OrcamentoServiceGrpc.OrcamentoServiceImplBase {

    @Override
    public void fazerOrcamento(OrcamentoData request, StreamObserver<ResultOrcamento> responseObserver) {
        super.fazerOrcamento(request, responseObserver);
    }
}
