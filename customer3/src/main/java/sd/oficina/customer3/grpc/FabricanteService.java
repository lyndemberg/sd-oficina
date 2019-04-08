package sd.oficina.customer3.grpc;

import com.google.protobuf.Empty;
import sd.oficina.customer3.dao.FabricanteDao;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import io.grpc.stub.StreamObserver;
import sd.oficina.shared.model.customer.Fabricante;
import sd.oficina.shared.proto.customer.*;

import java.util.List;

public class FabricanteService extends FabricanteServiceGrpc.FabricanteServiceImplBase{

    private FabricanteDao dao;

    public FabricanteService() {
        this.dao = new FabricanteDao();
    }

    @Override
    public void salvar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        Fabricante resultado = this.dao.salvar(ProtoConverterCustomer.protoToModel(request));
        FabricanteResult fabricante = FabricanteResult.newBuilder()
                .setCodigo(200)
                .setFabricante(ProtoConverterCustomer.modelToProto(resultado))
                .build();
        //
        responseObserver.onNext(fabricante);
        responseObserver.onCompleted();
    }

    @Override
    public void deletar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        this.dao.remover(request.getId());
        responseObserver.onNext(FabricanteResult.newBuilder().setCodigo(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void atualizar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        Fabricante fabricante = this.dao.atualizar(ProtoConverterCustomer.protoToModel(request));
        //
        responseObserver.onNext(FabricanteResult
                .newBuilder()
                .setFabricante(
                        fabricante != null ? ProtoConverterCustomer.modelToProto(fabricante) : FabricanteProto.newBuilder().build()
                )
                .build());
        //
        responseObserver.onCompleted();
    }

    @Override
    public void buscar(FabricanteProto request, StreamObserver<FabricanteResult> responseObserver) {
        Fabricante fabricante = this.dao.getById(request.getId());
        responseObserver.onNext(FabricanteResult
                .newBuilder()
                .setFabricante(
                        fabricante != null ? ProtoConverterCustomer.modelToProto(fabricante) : FabricanteProto.newBuilder().build())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void buscarTodos(Empty request, StreamObserver<FabricanteProtoList> responseObserver) {
        List<Fabricante> anos = this.dao.getAll();
        //
        final FabricanteProtoList.Builder builder = FabricanteProtoList.newBuilder();
        anos.forEach(a -> builder.addFabricantes(ProtoConverterCustomer.modelToProto(a)));
        //
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}