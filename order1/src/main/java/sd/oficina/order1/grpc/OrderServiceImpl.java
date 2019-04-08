package sd.oficina.order1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.order1.dao.OrdemServicoDao;
import sd.oficina.shared.converter.ProtoConverterOrder;
import sd.oficina.shared.model.order.OrdemServico;
import sd.oficina.shared.proto.order.ClienteProto;
import sd.oficina.shared.proto.order.OrdemProto;
import sd.oficina.shared.proto.order.OrderServiceGrpc;

import java.util.List;

public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    private final OrdemServicoDao ordemServicoDao;

    public OrderServiceImpl(){
        ordemServicoDao = new OrdemServicoDao();
    }

    @Override
    public void cadastrarNovaOrdem(OrdemProto request, StreamObserver<Empty> responseObserver) {
        OrdemServico model = new OrdemServico();
        ordemServicoDao.salvar(model);
        responseObserver.onCompleted();
    }

    @Override
    public void realizarPagamento(OrdemProto request, StreamObserver<OrdemProto> responseObserver) {
        OrdemServico ordemServico = ordemServicoDao.buscarPorId(request.getIdPedido());
        ordemServico.setPago(true);
        OrdemServico atualizado = ordemServicoDao.atualizar(ordemServico);
        responseObserver.onNext(ProtoConverterOrder.modelToProto(atualizado));
        responseObserver.onCompleted();
    }

    @Override
    public void concluirOrdem(OrdemProto request, StreamObserver<OrdemProto> responseObserver) {
        OrdemServico ordemServico = ordemServicoDao.buscarPorId(request.getIdPedido());
        ordemServico.setConcluida(true);
        OrdemServico atualizado = ordemServicoDao.atualizar(ordemServico);
        responseObserver.onNext(ProtoConverterOrder.modelToProto(atualizado));
        responseObserver.onCompleted();
    }

    @Override
    public void buscarOrdensPorCliente(ClienteProto request, StreamObserver<OrdemProto> responseObserver) {
        List<OrdemServico> ordemServicos = ordemServicoDao.buscarOrdensPorCliente(request.getId());
        for(int i=0; i<ordemServicos.size();i++){
            OrdemServico ordem = ordemServicos.get(i);
            responseObserver.onNext(ProtoConverterOrder.modelToProto(ordem));
        }
        responseObserver.onCompleted();
    }

}
