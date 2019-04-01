package sd.oficina.customer1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.customer1.dao.FabricanteDao;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.shared.converter.ProtoConverter;
import sd.oficina.shared.model.Fabricante;
import sd.oficina.shared.proto.FabricanteProto;
import sd.oficina.shared.proto.FabricanteProtoList;
import sd.oficina.shared.proto.FabricanteResult;
import sd.oficina.shared.proto.FabricanteServiceGrpc;

import java.io.Serializable;
import java.util.Optional;

public final class FabricanteService extends FabricanteServiceGrpc.FabricanteServiceImplBase implements Serializable {

    private final FabricanteDao fabricanteDao;

    public FabricanteService() {
        this.fabricanteDao = new FabricanteDao();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<FabricanteProtoList> responseObserver) {

        final FabricanteProtoList.Builder builder = FabricanteProtoList.newBuilder();

        fabricanteDao.listarTodos()
                .forEach(fabricante -> builder.addFabricantes(
                        ProtoConverter.modelToProto(fabricante)
                ));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {

        Fabricante fabricante = ProtoConverter.protoToModel(request);

        try {
            Optional<Fabricante> optional = this.fabricanteDao.salvar(fabricante);

            if (optional.isPresent()) {

                Fabricante fabricanteSalvo = optional.get();

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(200)
                                .setFabricante(
                                        ProtoConverter.modelToProto(fabricanteSalvo)
                                )
                                .build()
                );

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

        Fabricante fabricante = ProtoConverter.protoToModel(request);

        try {
            Optional<Fabricante> optional = this.fabricanteDao.atualizar(fabricante);

            if (optional.isPresent()) {

                Fabricante fabricanteSalvo = optional.get();

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(200)
                                .setFabricante(
                                        ProtoConverter.modelToProto(fabricanteSalvo)
                                )
                                .build()
                );

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

        Fabricante fabricante = ProtoConverter.protoToModel(request);

        try {
            Boolean foiRemovido = this.fabricanteDao.remover(fabricante.getId());

            if (foiRemovido) {

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(200)
                                .build()
                );
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

        Fabricante fabricante = ProtoConverter.protoToModel(request);

        try {

            Optional<Fabricante> optional = this.fabricanteDao.buscarPorId(fabricante.getId());

            if (optional.isPresent()) {

                Fabricante fabricanteDB = optional.get();

                responseObserver.onNext(
                        FabricanteResult
                                .newBuilder()
                                .setCodigo(200)
                                .setFabricante(
                                        ProtoConverter.modelToProto(fabricanteDB)
                                )
                                .build()
                );
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
