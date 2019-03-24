package converter;

import br.edu.ifpb.model.Fabricante;

public class ProtoConverter {

    public static Fabricante protoToModel(br.edu.ifpb.proto.FabricanteProto proto) {

        Fabricante fabricante = new Fabricante();
        fabricante.setId(proto.getId() != 0 ? proto.getId() : 0);
        fabricante.setNome(proto.getNome() != null ? proto.getNome() : null);
        return fabricante;
    }

    public static br.edu.ifpb.proto.FabricanteProto modelToProto(Fabricante model) {
        return br.edu.ifpb.proto.FabricanteProto.newBuilder()
                    .setId(model.getId() != 0 ? model.getId() : 0)
                .setNome(model.getNome() != null ? model.getNome() : "")
                .build();
    }
}
