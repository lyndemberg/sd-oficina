package sd.oficina.order1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.order1.cache.ConnectionFactory;
import sd.oficina.order1.dao.OrdemServicoDao;
import sd.oficina.shared.converter.ProtoConverterOrder;
import sd.oficina.shared.model.order.OrdemServico;
import sd.oficina.shared.proto.order.ClienteProto;
import sd.oficina.shared.proto.order.OrdemProto;
import sd.oficina.shared.proto.order.OrderServiceGrpc;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    private final OrdemServicoDao ordemServicoDao;
    //CACHE
    private final RedisTemplate<String,Object> redisTemplate;
    private final HashOperations<String,Object, Object> hashOperations;

    public OrderServiceImpl(){
        this.ordemServicoDao = new OrdemServicoDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void cadastrarNovaOrdem(OrdemProto request, StreamObserver<Empty> responseObserver) {
        OrdemServico model = new OrdemServico();
        ordemServicoDao.salvar(model);
        responseObserver.onCompleted();
    }

    @Override
    public void realizarPagamento(OrdemProto request, StreamObserver<OrdemProto> responseObserver) {
        OrdemServico ordemServico = ordemServicoDao.buscarPorId(request.getIdOrdem());
        ordemServico.setPago(true);
        OrdemServico atualizado = ordemServicoDao.atualizar(ordemServico);
        responseObserver.onNext(ProtoConverterOrder.modelToProto(atualizado));
        responseObserver.onCompleted();
    }

    @Override
    public void concluirOrdem(OrdemProto request, StreamObserver<OrdemProto> responseObserver) {
        OrdemServico ordemServico = ordemServicoDao.buscarPorId(request.getIdOrdem());
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

        // Finaliza comunicaçao
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o Cache
        hashOperations.put(OrdemServico.class.getSimpleName(), request.getId(), ordemServicos);
    }

}
