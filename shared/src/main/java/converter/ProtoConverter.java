package converter;

import br.edu.ifpb.model.Cor;
import br.edu.ifpb.model.Fabricante;
import br.edu.ifpb.model.Modelo;
import br.edu.ifpb.model.Veiculo;
import br.edu.ifpb.proto.CorProto;
import br.edu.ifpb.proto.ModeloProto;
import br.edu.ifpb.proto.VeiculoProto;

public class ProtoConverter {


    // CONVERSÃO DE PROTOS PARA CLASSES DE MODELO
    public static Fabricante protoToModel(br.edu.ifpb.proto.FabricanteProto proto) {

        Fabricante fabricante = new Fabricante();
        fabricante.setId(proto.getId() != 0 ? proto.getId() : 0);
        fabricante.setNome(proto.getNome() != null ? proto.getNome() : null);
        return fabricante;
    }

    public static Veiculo protoToModel(br.edu.ifpb.proto.VeiculoProto proto) {

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(proto.getPlaca() != null ? proto.getPlaca() : "");
        veiculo.setQuilometragem(proto.getQuilometragem() != 0 ? proto.getQuilometragem() : 0);
        veiculo.setCor(protoToModel(proto.getCor()));
        veiculo.setModelo(protoToModel(proto.getModelo()));
        return veiculo;
    }

    public static Cor protoToModel(br.edu.ifpb.proto.CorProto proto) {
        Cor cor = new Cor();
        cor.setNome(proto.getNome() != null ? proto.getNome() : "");
        cor.setId(proto.getId() != 0 ? proto.getId() : 0);
        return cor;
    }

    public static Modelo protoToModel(br.edu.ifpb.proto.ModeloProto proto) {

        Modelo modelo = new Modelo();
        modelo.setTipo(proto.getTipo() != null ? proto.getTipo() : "");
        modelo.setNome(proto.getNome() != null ? proto.getNome() : "");
        modelo.setFabricante(protoToModel(proto.getFabricante()));
        modelo.setId(proto.getId() != 0 ? proto.getId() : 0);
        return modelo;
    }


    // CONVERSÃO DE CLASSES DE MODELO PARA PROTO
    public static br.edu.ifpb.proto.FabricanteProto modelToProto(Fabricante model) {
        return br.edu.ifpb.proto.FabricanteProto.newBuilder()
                .setId(model.getId() != 0 ? model.getId() : 0)
                .setNome(model.getNome() != null ? model.getNome() : "")
                .build();
    }

    public static br.edu.ifpb.proto.VeiculoProto modelToProto(Veiculo model) {
        return VeiculoProto.newBuilder()
                .setCor(modelToProto(model.getCor()))
                .setModelo(modelToProto(model.getModelo()))
                .setId(model.getId() != 0 ? model.getId() : 0)
                .setQuilometragem(model.getQuilometragem() != 0 ? model.getQuilometragem() : 0)
                .setPlaca(model.getPlaca() != null ? model.getPlaca() : "")
                .build();
    }

    public static br.edu.ifpb.proto.CorProto modelToProto(Cor model) {
        return CorProto.newBuilder()
                .setId(model.getId() != 0 ? model.getId() : 0)
                .setNome(model.getNome() != null ? model.getNome() : "")
                .build();
    }

    public static br.edu.ifpb.proto.ModeloProto modelToProto(Modelo model) {
        return ModeloProto.newBuilder()
                .setId(model.getId() != 0 ? model.getId() : 0)
                .setNome(model.getNome() != null ? model.getNome() : "")
                .setTipo(model.getTipo() != null ? model.getTipo() : "")
                .setFabricante(modelToProto(model.getFabricante()))
                .build();
    }
}
