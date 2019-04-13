package sd.oficina.customer1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer1.dao.ModeloDao;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.customer1.infra.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import sd.oficina.shared.model.customer.Modelo;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.proto.customer.ModeloProto;
import sd.oficina.shared.proto.customer.ModeloProtoList;
import sd.oficina.shared.proto.customer.ModeloResult;
import sd.oficina.shared.proto.customer.ModeloServiceGrpc;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ModeloService extends ModeloServiceGrpc.ModeloServiceImplBase implements Serializable {

    private final ModeloDao modeloDao;

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;

    public ModeloService() {
        this.modeloDao = new ModeloDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<ModeloProtoList> responseObserver) {

        final ModeloProtoList.Builder builder = ModeloProtoList.newBuilder();

        // Recuper lista de veiculos do DB
        List<Modelo> modelos = modeloDao.listarTodos();

        // Gera a resposta
        modelos.forEach(modelo -> builder.addModelos(
                ProtoConverterCustomer.modelToProto(modelo)
        ));

        // Envia Resposta ao cliente
        responseObserver.onNext(builder.build());

        // Finaliza comunicaçao
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Modelo.class.getSimpleName(),
                modelos.stream().collect(
                        Collectors.toMap(Modelo::getId, modelo -> modelo)
                ));
    }

    @Override
    public void salvar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {

        Modelo modelo = ProtoConverterCustomer.protoToModel(request);

        try {
            Optional<Modelo> optional = this.modeloDao.salvar(modelo);

            if (optional.isPresent()) {

                Modelo modeloSalvo = optional.get();

                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(200)
                                .setModelo(
                                        ProtoConverterCustomer.modelToProto(modeloSalvo)
                                )
                                .build()
                );

            } else {
                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(400)
                                .build()
                );

            }

        } catch (AtributoIdInvalidoException | TentaPersistirObjetoNullException ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    ModeloResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    ModeloResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {

        Modelo modelo = ProtoConverterCustomer.protoToModel(request);

        try {
            Optional<Modelo> optional = this.modeloDao.atualizar(modelo);

            if (optional.isPresent()) {

                Modelo modeloSalvo = optional.get();

                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(200)
                                .setModelo(
                                        ProtoConverterCustomer.modelToProto(modeloSalvo)
                                )
                                .build()
                );

            } else {
                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(400)
                                .build()
                );

            }

        } catch (AtributoIdInvalidoException | TentaPersistirObjetoNullException ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    ModeloResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    ModeloResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deletar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {

        Modelo modelo = ProtoConverterCustomer.protoToModel(request);

        try {
            Boolean foiRemovido = this.modeloDao.remover(modelo.getId());

            if (foiRemovido) {

                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(200)
                                .build()
                );
            } else {

                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(204)
                                .build()
                );
            }


        } catch (AtributoIdInvalidoException aiiEx) {
            aiiEx.printStackTrace();

            responseObserver.onNext(
                    ModeloResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    ModeloResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void buscar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {

        Modelo modelo = ProtoConverterCustomer.protoToModel(request);

        try {

            Optional<Modelo> optional = this.modeloDao.buscarPorId(modelo.getId());

            if (optional.isPresent()) {

                Modelo modeloDB = optional.get();

                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(200)
                                .setModelo(
                                        ProtoConverterCustomer.modelToProto(modeloDB)
                                )
                                .build()
                );

                // Atualiza o cache
                hashOperations.put(Modelo.class.getSimpleName(), modelo.getId(), modelo);

            } else {

                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(204)
                                .build()
                );
            }


        } catch (NullPointerException | AtributoIdInvalidoException ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    ModeloResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );
        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    ModeloResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );


        }

        responseObserver.onCompleted();
    }

}
