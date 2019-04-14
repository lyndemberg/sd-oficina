package sd.oficina.person3.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import sd.oficina.person3.daos.CidadeDao;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.proto.person.CidadeList;
import sd.oficina.shared.proto.person.CidadeProto;
import sd.oficina.shared.proto.person.CidadeResult;
import sd.oficina.shared.proto.person.CidadeServiceGrpc;

import java.util.List;

public class CidadeService extends CidadeServiceGrpc.CidadeServiceImplBase {

    private CidadeDao cidadeDao;

    public CidadeService() {
        this.cidadeDao = new CidadeDao();
    }

    @Override
    public void salvar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        Cidade cidade = this.cidadeDao.salvar(ProtoConverterPerson.protoToModel(request));

        if (!cidade.equals(null)) {
            responseObserver.onNext(
                    CidadeResult.newBuilder()
                            .setCodigo(200)
                            .setCidade(ProtoConverterPerson.modelToProto(cidade))
                            .build());
        } else {
            responseObserver.onNext(
                    CidadeResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        Cidade cidade = this.cidadeDao.atualizar(ProtoConverterPerson.protoToModel(request));

        if (!cidade.equals(null)) {
            responseObserver
                    .onNext(CidadeResult.newBuilder()
                            .setCodigo(200)
                            .setCidade(ProtoConverterPerson.modelToProto(cidade)).build());
        } else {
            responseObserver
                    .onNext(CidadeResult.newBuilder()
                            .setCodigo(400)
                            .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deletar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        this.cidadeDao.deletar(ProtoConverterPerson.protoToModel(request));
        responseObserver.
                onNext(CidadeResult.newBuilder()
                        .setCodigo(200)
                        .build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(CidadeProto request, StreamObserver<CidadeResult> responseObserver) {
        Cidade cidade = this.cidadeDao.buscar(ProtoConverterPerson.protoToModel(request));

        if (!cidade.equals(null)) {
            responseObserver.onNext(CidadeResult
                    .newBuilder()
                    .setCodigo(200)
                    .setCidade(
                            ProtoConverterPerson.modelToProto(cidade))
                    .build());
        } else {
            responseObserver.onNext(CidadeResult
                    .newBuilder()
                    .setCodigo(400)
                    .build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void listar(Empty request, StreamObserver<CidadeList> responseObserver) {
        List<Cidade> cidades = this.cidadeDao.listar();

        CidadeList.Builder builder = CidadeList.newBuilder();
        for (Cidade cidade : cidades) {
            builder.addCidades(ProtoConverterPerson.modelToProto(cidade));
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
