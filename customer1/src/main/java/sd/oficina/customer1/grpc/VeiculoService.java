package sd.oficina.customer1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer1.dao.VeiculoDao;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.customer1.infra.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.model.order.OrdemServico;
import sd.oficina.shared.proto.customer.VeiculoProto;
import sd.oficina.shared.proto.customer.VeiculoProtoList;
import sd.oficina.shared.proto.customer.VeiculoResult;
import sd.oficina.shared.proto.customer.VeiculoServiceGrpc;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class VeiculoService extends VeiculoServiceGrpc.VeiculoServiceImplBase implements Serializable {

    private final VeiculoDao veiculoDao;

    private final RedisTemplate<String, Veiculo> redisTemplate;
    private final HashOperations<String, Object, Veiculo> hashOperations;

    public VeiculoService() {
        this.veiculoDao = new VeiculoDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<VeiculoProtoList> responseObserver) {

        final VeiculoProtoList.Builder builder = VeiculoProtoList.newBuilder();

        // Recuper lista de veiculos do DB
        List<Veiculo> veiculos = veiculoDao.listarTodos();

        // Gera a resposta
        veiculos.forEach(veiculo -> builder.addVeiculos(
                ProtoConverterCustomer.modelToProto(veiculo)
        ));

        // Envia Resposta ao cliente
        responseObserver.onNext(builder.build());

        // Finaliza comunicaçao
        responseObserver.onCompleted();

    }

    @Override
    public void salvar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {

        Veiculo veiculo = ProtoConverterCustomer.protoToModel(request);

        try {
            Optional<Veiculo> optional = this.veiculoDao.salvar(veiculo);

            if (optional.isPresent()) {

                Veiculo veiculoSalvo = optional.get();

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(200)
                                .setVeiculo(
                                        ProtoConverterCustomer.modelToProto(veiculoSalvo)
                                )
                                .build()
                );
                this.hashOperations.put(Veiculo.class.getSimpleName(),veiculo.getId(),veiculo);

            } else {
                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(400)
                                .build()
                );

            }

        } catch (AtributoIdInvalidoException | TentaPersistirObjetoNullException ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    VeiculoResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    VeiculoResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {

        Veiculo veiculo = ProtoConverterCustomer.protoToModel(request);

        try {
            Optional<Veiculo> optional = this.veiculoDao.atualizar(veiculo);

            if (optional.isPresent()) {

                Veiculo veiculoSalvo = optional.get();

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(200)
                                .setVeiculo(
                                        ProtoConverterCustomer.modelToProto(veiculoSalvo)
                                )
                                .build()
                );

                this.hashOperations.put(Veiculo.class.getSimpleName(),veiculo.getId(),veiculo);

            } else {
                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(400)
                                .build()
                );

            }

        } catch (AtributoIdInvalidoException | TentaPersistirObjetoNullException ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    VeiculoResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    VeiculoResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deletar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {

        Veiculo veiculo = ProtoConverterCustomer.protoToModel(request);

        try {
            Boolean foiRemovido = this.veiculoDao.remover(veiculo.getId());

            if (foiRemovido) {

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(200)
                                .build()
                );
                this.hashOperations.delete(Veiculo.class.getSimpleName(),veiculo.getId());
            } else {

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(204)
                                .build()
                );
            }


        } catch (AtributoIdInvalidoException aiiEx) {
            aiiEx.printStackTrace();

            responseObserver.onNext(
                    VeiculoResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    VeiculoResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void buscar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {

        Veiculo veiculo = ProtoConverterCustomer.protoToModel(request);

        try {

            Optional<Veiculo> optional = this.veiculoDao.buscarPorId(veiculo.getId());

            if (optional.isPresent()) {

                Veiculo veiculoDB = optional.get();

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(200)
                                .setVeiculo(
                                        ProtoConverterCustomer.modelToProto(veiculoDB)
                                )
                                .build()
                );

            } else {

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(204)
                                .build()
                );
            }


        } catch (NullPointerException | AtributoIdInvalidoException ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    VeiculoResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );
        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    VeiculoResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );


        }

        responseObserver.onCompleted();
    }

}
