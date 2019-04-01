package sd.oficina.customer1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.customer1.dao.VeiculoDao;
import sd.oficina.customer1.exceptions.AtributoIdInvalidoException;
import sd.oficina.customer1.exceptions.TentaPersistirObjetoNullException;
import sd.oficina.shared.converter.ProtoConverter;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.proto.customer.VeiculoProto;
import sd.oficina.shared.proto.customer.VeiculoProtoList;
import sd.oficina.shared.proto.customer.VeiculoResult;
import sd.oficina.shared.proto.customer.VeiculoServiceGrpc;

import java.io.Serializable;
import java.util.Optional;

public final class VeiculoService extends VeiculoServiceGrpc.VeiculoServiceImplBase implements Serializable {

    private final VeiculoDao veiculoDao;

    public VeiculoService() {
        this.veiculoDao = new VeiculoDao();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<VeiculoProtoList> responseObserver) {

        final VeiculoProtoList.Builder builder = VeiculoProtoList.newBuilder();

        veiculoDao.listarTodos()
                .forEach(veiculo -> builder.addVeiculos(
                        ProtoConverter.modelToProto(veiculo)
                ));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(VeiculoProto request, StreamObserver<VeiculoResult> responseObserver) {

        Veiculo veiculo = ProtoConverter.protoToModel(request);

        try {
            Optional<Veiculo> optional = this.veiculoDao.salvar(veiculo);

            if (optional.isPresent()) {

                Veiculo veiculoSalvo = optional.get();

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(200)
                                .setVeiculo(
                                        ProtoConverter.modelToProto(veiculoSalvo)
                                )
                                .build()
                );

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

        Veiculo veiculo = ProtoConverter.protoToModel(request);

        try {
            Optional<Veiculo> optional = this.veiculoDao.atualizar(veiculo);

            if (optional.isPresent()) {

                Veiculo veiculoSalvo = optional.get();

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(200)
                                .setVeiculo(
                                        ProtoConverter.modelToProto(veiculoSalvo)
                                )
                                .build()
                );

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

        Veiculo veiculo = ProtoConverter.protoToModel(request);

        try {
            Boolean foiRemovido = this.veiculoDao.remover(veiculo.getId());

            if (foiRemovido) {

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(200)
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

        Veiculo veiculo = ProtoConverter.protoToModel(request);

        try {

            Optional<Veiculo> optional = this.veiculoDao.buscarPorId(veiculo.getId());

            if (optional.isPresent()) {

                Veiculo veiculoDB = optional.get();

                responseObserver.onNext(
                        VeiculoResult
                                .newBuilder()
                                .setCodigo(200)
                                .setVeiculo(
                                        ProtoConverter.modelToProto(veiculoDB)
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
