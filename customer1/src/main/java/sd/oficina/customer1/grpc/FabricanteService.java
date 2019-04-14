package sd.oficina.customer1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import sd.oficina.customer1.dao.FabricanteDao;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.customer1.infra.cache.ConnectionFactory;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import sd.oficina.shared.model.customer.Fabricante;
import sd.oficina.shared.proto.customer.FabricanteProto;
import sd.oficina.shared.proto.customer.FabricanteProtoList;
import sd.oficina.shared.proto.customer.FabricanteResult;
import sd.oficina.shared.proto.customer.FabricanteServiceGrpc;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class FabricanteService extends FabricanteServiceGrpc.FabricanteServiceImplBase implements Serializable {

    private final FabricanteDao fabricanteDao;

    private final RedisTemplate<String, Fabricante> redisTemplate;
    private final HashOperations<String, Object, Fabricante> hashOperations;

    public FabricanteService() {
        this.fabricanteDao = new FabricanteDao();
        this.redisTemplate = ConnectionFactory.getRedisTemplate();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<FabricanteProtoList> responseObserver) {

        final FabricanteProtoList.Builder builder = FabricanteProtoList.newBuilder();

        // Recuper lista de veiculos do DB
        List<Fabricante> fabricantes = fabricanteDao.listarTodos();

        // Gera a resposta
        fabricantes.forEach(fabricante -> builder.addFabricantes(
                ProtoConverterCustomer.modelToProto(fabricante)
        ));

        // Envia Resposta ao cliente
        responseObserver.onNext(builder.build());

        // Finaliza comunicaçao
        responseObserver.onCompleted();

        // Apos finalizar a comunicaçao atualiza o cache
        hashOperations.putAll(
                Fabricante.class.getSimpleName(),
                fabricantes.stream().collect(
                        Collectors.toMap(Fabricante::getId, fabricante -> fabricante)
                ));
    }

    @Override
    public void salvar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {

        Fabricante fabricante = ProtoConverterCustomer.protoToModel(request);

        try {
            Optional<Fabricante> optional = this.fabricanteDao.salvar(fabricante);

            if (optional.isPresent()) {

                Fabricante fabricanteSalvo = optional.get();

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(200)
                                .setFabricante(
                                        ProtoConverterCustomer.modelToProto(fabricanteSalvo)
                                )
                                .build()
                );
                this.hashOperations.put(Fabricante.class.getSimpleName(),fabricante.getId(),fabricante);

            } else {
                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(400)
                                .build()
                );

            }

        } catch (AtributoIdInvalidoException | TentaPersistirObjetoNullException ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    FabricanteResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    FabricanteResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {

        Fabricante fabricante = ProtoConverterCustomer.protoToModel(request);

        try {
            Optional<Fabricante> optional = this.fabricanteDao.atualizar(fabricante);

            if (optional.isPresent()) {

                Fabricante fabricanteSalvo = optional.get();

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(200)
                                .setFabricante(
                                        ProtoConverterCustomer.modelToProto(fabricanteSalvo)
                                )
                                .build()
                );
                this.hashOperations.put(Fabricante.class.getSimpleName(),fabricante.getId(),fabricante);

            } else {
                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(400)
                                .build()
                );

            }

        } catch (AtributoIdInvalidoException | TentaPersistirObjetoNullException ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    FabricanteResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    FabricanteResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deletar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {

        Fabricante fabricante = ProtoConverterCustomer.protoToModel(request);

        try {
            Boolean foiRemovido = this.fabricanteDao.remover(fabricante.getId());

            if (foiRemovido) {

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(200)
                                .build()
                );
                this.hashOperations.delete(Fabricante.class.getSimpleName(),fabricante.getId());
            } else {

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(204)
                                .build()
                );
            }


        } catch (AtributoIdInvalidoException aiiEx) {
            aiiEx.printStackTrace();

            responseObserver.onNext(
                    FabricanteResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    FabricanteResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );
        }

        responseObserver.onCompleted();
    }

    @Override
    public void buscar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {

        Fabricante fabricante = ProtoConverterCustomer.protoToModel(request);

        try {

            Optional<Fabricante> optional = this.fabricanteDao.buscarPorId(fabricante.getId());

            if (optional.isPresent()) {

                Fabricante fabricanteDB = optional.get();

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(200)
                                .setFabricante(
                                        ProtoConverterCustomer.modelToProto(fabricanteDB)
                                )
                                .build()
                );

                // Atualiza o cache
                hashOperations.put(Fabricante.class.getSimpleName(), fabricante.getId(), fabricante);

            } else {

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(204)
                                .build()
                );
            }


        } catch (NullPointerException | AtributoIdInvalidoException ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    FabricanteResult
                            .newBuilder()
                            .setCodigo(400)
                            .build()
            );
        } catch (Exception ex) {
            ex.printStackTrace();

            responseObserver.onNext(
                    FabricanteResult
                            .newBuilder()
                            .setCodigo(500)
                            .build()
            );


        }

        responseObserver.onCompleted();
    }

}
