package sd.oficina.customer1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.customer1.dao.ModeloDao;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.shared.converter.ProtoConverter;
import sd.oficina.shared.model.Modelo;
import sd.oficina.shared.proto.ModeloProto;
import sd.oficina.shared.proto.ModeloProtoList;
import sd.oficina.shared.proto.ModeloResult;
import sd.oficina.shared.proto.ModeloServiceGrpc;

import java.io.Serializable;
import java.util.Optional;

public final class ModeloService extends ModeloServiceGrpc.ModeloServiceImplBase implements Serializable {

    private final ModeloDao modeloDao;

    public ModeloService(final ModeloDao modeloDao) {
        this.modeloDao = modeloDao;
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<ModeloProtoList> responseObserver) {

        final ModeloProtoList.Builder builder = ModeloProtoList.newBuilder();

        modeloDao.listarTodos()
                .forEach(modelo -> builder.addModelos(
                        ProtoConverter.modelToProto(modelo)
                ));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(ModeloProto request, StreamObserver<ModeloResult> responseObserver) {

        Modelo modelo = ProtoConverter.protoToModel(request);

        try {
            Optional<Modelo> optional = this.modeloDao.salvar(modelo);

            if (optional.isPresent()) {

                Modelo modeloSalvo = optional.get();

                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(200)
                                .setModelo(
                                        ProtoConverter.modelToProto(modeloSalvo)
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

        Modelo modelo = ProtoConverter.protoToModel(request);

        try {
            Optional<Modelo> optional = this.modeloDao.atualizar(modelo);

            if (optional.isPresent()) {

                Modelo modeloSalvo = optional.get();

                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(200)
                                .setModelo(
                                        ProtoConverter.modelToProto(modeloSalvo)
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

        Modelo modelo = ProtoConverter.protoToModel(request);

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

        Modelo modelo = ProtoConverter.protoToModel(request);

        try {

            Optional<Modelo> optional = this.modeloDao.buscarPorId(modelo.getId());

            if (optional.isPresent()) {

                Modelo modeloDB = optional.get();

                responseObserver.onNext(
                        ModeloResult
                                .newBuilder()
                                .setCodigo(200)
                                .setModelo(
                                        ProtoConverter.modelToProto(modeloDB)
                                )
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
