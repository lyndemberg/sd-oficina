package sd.oficina.person2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.person2.dao.FornecedorDao;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Fornecedor;
import sd.oficina.shared.proto.person.FornecedorList;
import sd.oficina.shared.proto.person.FornecedorProto;
import sd.oficina.shared.proto.person.FornecedorResult;
import sd.oficina.shared.proto.person.FornecedorServiceGrpc;

import java.util.List;

public class FornecedorService extends FornecedorServiceGrpc.FornecedorServiceImplBase {

    private FornecedorDao dao;

    public FornecedorService() {
        this.dao = new FornecedorDao();
    }

    @Override
    public void salvar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor resultado = this.dao.salvar(ProtoConverterPerson.protoToModel(request));
        //
        FornecedorResult estado = FornecedorResult.newBuilder()
                .setCodigo(200)
                .setFornecedor(ProtoConverterPerson.modelToProto(resultado))
                .build();
        //
        responseObserver.onNext(estado);
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(FornecedorResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.dao.atualizar(ProtoConverterPerson.protoToModel(request));
        //
        responseObserver.onNext(FornecedorResult
                .newBuilder()
                .setFornecedor(
                        fornecedor != null ? ProtoConverterPerson.modelToProto(fornecedor) : FornecedorProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(FornecedorProto request, StreamObserver<FornecedorResult> responseObserver) {
        Fornecedor fornecedor = this.dao.getById(request.getId());
        responseObserver.onNext(FornecedorResult
                .newBuilder()
                .setFornecedor(
                        fornecedor != null ? ProtoConverterPerson.modelToProto(fornecedor) : FornecedorProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<FornecedorList> responseObserver) {
        List<Fornecedor> estados = this.dao.getAll();
        //
        FornecedorList.Builder builder = FornecedorList.newBuilder();
        for (Fornecedor estado : estados) {
            builder.addFornecedores(ProtoConverterPerson.modelToProto(estado));
        }
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
