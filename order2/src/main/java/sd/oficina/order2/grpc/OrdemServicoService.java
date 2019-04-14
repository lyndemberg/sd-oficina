package sd.oficina.order2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.order2.dao.OrdemServicoDao;
import sd.oficina.order2.exceptions.AtributoIdInvalidoException;
import sd.oficina.order2.infra.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterOrder;
import sd.oficina.shared.model.order.OrdemServico;
import sd.oficina.shared.proto.order.ClienteProto;
import sd.oficina.shared.proto.order.OrdemProto;
import sd.oficina.shared.proto.order.OrderServiceGrpc;

import java.util.List;
import java.util.Optional;

public class OrdemServicoService extends OrderServiceGrpc.OrderServiceImplBase {

    private OrdemServicoDao ordemServicoDao;
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;

    public OrdemServicoService() {
        this.ordemServicoDao = new OrdemServicoDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void cadastrarNovaOrdem(OrdemProto request, StreamObserver<Empty> responseObserver) {
        OrdemServico ordemServico = ProtoConverterOrder.protoToModel(request);

        try {
            this.ordemServicoDao.salvar(ordemServico);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        responseObserver.onCompleted();
    }

    @Override
    public void realizarPagamento(OrdemProto request, StreamObserver<OrdemProto> responseObserver) {

        Optional<OrdemServico> optional = Optional.empty();

        try {
            optional = ordemServicoDao.buscarPorId(request.getIdOrdem());

        } catch (AtributoIdInvalidoException aiiEx) {
            aiiEx.printStackTrace();
        }

        if (optional.isPresent()) {

            OrdemServico ordemServico = optional.get();

            ordemServico.setPago(true);

            try {
                Optional<OrdemServico> atualizado = this.ordemServicoDao.atualizar(ordemServico);

                atualizado.ifPresent(ordemServico1 -> responseObserver.onNext(ProtoConverterOrder.modelToProto(ordemServico1)));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        responseObserver.onCompleted();
    }

    @Override
    public void concluirOrdem(OrdemProto request, StreamObserver<OrdemProto> responseObserver) {

        Optional<OrdemServico> optional = Optional.empty();

        try {
            optional = ordemServicoDao.buscarPorId(request.getIdOrdem());

        } catch (AtributoIdInvalidoException aiiEx) {
            aiiEx.printStackTrace();
        }

        if (optional.isPresent()) {

            OrdemServico ordemServico = optional.get();

            ordemServico.setConcluida(true);

            try {
                Optional<OrdemServico> atualizado = this.ordemServicoDao.atualizar(ordemServico);

                atualizado.ifPresent(ordemServico1 -> responseObserver.onNext(ProtoConverterOrder.modelToProto(ordemServico1)));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        responseObserver.onCompleted();
    }

    @Override
    public void listarOrdens(Empty request, StreamObserver<OrdemProto> responseObserver) {

        // Recupera todos os OrdemServico pelo Dao e para cada resultado envia a
        // informaçao pro cliente
        this.ordemServicoDao
                .listarTodos()
                .forEach(ordemServico -> responseObserver.onNext(ProtoConverterOrder.modelToProto(ordemServico)));

        // Finaliza comunicaçao
        responseObserver.onCompleted();
    }

    @Override
    public void buscarOrdensPorCliente(ClienteProto request, StreamObserver<OrdemProto> responseObserver) {
        List<OrdemServico> ordemServicos = this.ordemServicoDao.buscarPorClienteId(request.getId());

        // Envia respostas ao cliente
        ordemServicos.forEach(ordemServico -> responseObserver.onNext(ProtoConverterOrder.modelToProto(ordemServico)));

        // Finaliza comunicaçao
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o Cache
        hashOperations.put(OrdemServico.class.getSimpleName(), request.getId(), ordemServicos);
    }

}
