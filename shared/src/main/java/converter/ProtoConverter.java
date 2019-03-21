package converter;

import br.edu.ifpb.model.Fabricante;

public class ProtoConverter {

    public static Fabricante grpcToModel(br.edu.ifpb.proto.Fabricante proto){

        Fabricante fabricante = new Fabricante();
        fabricante.setId(proto.getId());
        fabricante.setNome(proto.getNome());
        return fabricante;
    }

    public static br.edu.ifpb.proto.Fabricante modelToGrpc(Fabricante model){
        return br.edu.ifpb.proto.Fabricante.newBuilder()
                .setId(model.getId())
                .setNome(model.getNome())
                .build();
    }
}
