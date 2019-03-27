package sd.oficina.customer2.grpc;

import sd.oficina.customer2.dao.FabricanteDAO;
import com.google.protobuf.Empty;
import sd.oficina.shared.converter.ProtoConverter;
import sd.oficina.shared.model.Fabricante;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.proto.FabricanteProto;
import sd.oficina.shared.proto.FabricanteProtoList;
import sd.oficina.shared.proto.FabricanteResult;
import sd.oficina.shared.proto.FabricanteServiceGrpc;

import java.util.List;

public class FabricanteImpl extends FabricanteServiceGrpc.FabricanteServiceImplBase {

    private FabricanteDAO dao;

    public FabricanteImpl() {
        this.dao = new FabricanteDAO();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<FabricanteProtoList> responseObserver) {
        List<Fabricante> fabricantes = dao.todos();
        final FabricanteProtoList.Builder builder = FabricanteProtoList.newBuilder();
        fabricantes.forEach(f-> builder.addFabricantes(ProtoConverter.modelToProto(f)));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void salvar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        Fabricante result = this.dao.salvar(ProtoConverter.protoToModel(request));
        FabricanteResult fabricanteResult = FabricanteResult.newBuilder()
                .setCodigo(200)
                .setFabricante(ProtoConverter.modelToProto(result))
                .build();
        responseObserver.onNext(fabricanteResult);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        Fabricante fabricante = dao.atualizar(ProtoConverter.protoToModel(request));
        responseObserver.onNext(FabricanteResult
                .newBuilder()
                .setFabricante(
                        fabricante != null ? ProtoConverter.modelToProto(fabricante) : FabricanteProto.newBuilder().build()
                )
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        this.dao.deletar(request.getId());
        responseObserver.onNext(FabricanteResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {

        Fabricante fabricante = dao.buscar(request.getId());
        responseObserver.onNext(FabricanteResult
                .newBuilder()
                .setFabricante(
                        fabricante != null ? ProtoConverter.modelToProto(fabricante) : FabricanteProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

}
