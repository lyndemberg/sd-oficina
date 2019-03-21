package br.edu.ifpb.grpc;

import converter.ProtoConverter;
import br.edu.ifpb.proto.Fabricante;
import br.edu.ifpb.proto.FabricanteServiceGrpc;
import br.edu.ifpb.dao.DAO;
import io.grpc.stub.StreamObserver;

public class FabricanteImpl extends FabricanteServiceGrpc.FabricanteServiceImplBase {

    private DAO dao;

    public FabricanteImpl() {
        this.dao = new DAO();
    }

    @Override
    public void salvar(Fabricante request, StreamObserver<Fabricante> responseObserver) {
        this.dao.salvar(ProtoConverter.grpcToModel(request));
        responseObserver.onNext(null);
        responseObserver.onCompleted();
    }
}
