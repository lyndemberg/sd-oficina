package sd.oficina.person2.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.person2.dao.CidadeDao;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.proto.person.CidadeList;
import sd.oficina.shared.proto.person.CidadeProto;
import sd.oficina.shared.proto.person.CidadeResult;
import sd.oficina.shared.proto.person.CidadeServiceGrpc;

import java.util.List;

public class CidadeService extends CidadeServiceGrpc.CidadeServiceImplBase {

    private CidadeDao dao;

    public CidadeService() {
        this.dao = new CidadeDao();
    }

    @Override
    public void salvar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        Cidade resultado = this.dao.salvar(ProtoConverterPerson.protoToModel(request));
        //
        CidadeResult cidade = CidadeResult.newBuilder()
                .setCodigo(200)
                .setCidade(ProtoConverterPerson.modelToProto(resultado))
                .build();
        //
        responseObserver.onNext(cidade);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        Cidade cidade = this.dao.atualizar(ProtoConverterPerson.protoToModel(request));
        //
        responseObserver.onNext(CidadeResult
                .newBuilder()
                .setCidade(
                        cidade != null ? ProtoConverterPerson.modelToProto(cidade) : CidadeProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(CidadeResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        Cidade cidade = this.dao.getById(request.getId());
        responseObserver.onNext(CidadeResult
                .newBuilder()
                .setCidade(
                        cidade != null ? ProtoConverterPerson.modelToProto(cidade) : CidadeProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<CidadeList> responseObserver) {
        List<Cidade> cidades = this.dao.getAll();
        //
        CidadeList.Builder builder = CidadeList.newBuilder();
        for (Cidade cidade : cidades) {
            builder.addCidades(ProtoConverterPerson.modelToProto(cidade));
        }
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
