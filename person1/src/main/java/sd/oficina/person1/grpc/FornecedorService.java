package sd.oficina.person1.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.person1.daos.FornecedorDao;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Fornecedor;
import sd.oficina.shared.proto.person.*;

import java.util.List;

public class FornecedorService extends FornecedorServiceGrpc.FornecedorServiceImplBase {

    private FornecedorDao fornecedorDao;

    public FornecedorService() {
        this.fornecedorDao = new FornecedorDao();
    }

    @Override
    public void salvar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.fornecedorDao.salvar(ProtoConverterPerson.protoToModel(request));

        if (!fornecedor.equals(null)) {
            responseObserver.onNext(
                    FornecedorResult.newBuilder()
                            .setCodigo(200)
                            .setFornecedor(ProtoConverterPerson.modelToProto(fornecedor))
                            .build());
        } else {
            responseObserver.onNext(
                    FornecedorResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.fornecedorDao.atualizar(ProtoConverterPerson.protoToModel(request));

        if (!fornecedor.equals(null)) {
            responseObserver
                    .onNext(FornecedorResult.newBuilder()
                            .setCodigo(200)
                            .setFornecedor(ProtoConverterPerson.modelToProto(fornecedor))
                            .build());
        } else {
            responseObserver
                    .onNext(FornecedorResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deletar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        this.fornecedorDao.deletar(ProtoConverterPerson.protoToModel(request));
        responseObserver.
                onNext(FornecedorResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.fornecedorDao.buscar(ProtoConverterPerson.protoToModel(request));

        if (!fornecedor.equals(null)) {
            responseObserver.onNext(FornecedorResult
                    .newBuilder()
                    .setCodigo(200)
                    .setFornecedor(
                            ProtoConverterPerson.modelToProto(fornecedor))
                    .build());
        } else {
            responseObserver.onNext(FornecedorResult
                    .newBuilder()
                    .setCodigo(400)
                    .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<FornecedorList> responseObserver) {
        List<Fornecedor> fornecedores = this.fornecedorDao.listar();

        FornecedorList.Builder builder = FornecedorList.newBuilder();
        for (Fornecedor fornecedor : fornecedores) {
            builder.addFornecedores(ProtoConverterPerson.modelToProto(fornecedor));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
