package sd.oficina.shared.converter;


import sd.oficina.shared.model.customer.*;
import sd.oficina.shared.proto.customer.*;

public class ProtoConverterCustomer {

    // CONVERSÃO DE PROTOS PARA CLASSES DE MODELO
    public static Fabricante protoToModel(FabricanteProto proto) {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(proto.getId());
        fabricante.setNome(proto.getNome() != null ? proto.getNome() : null);
        return fabricante;
    }

    public static Veiculo protoToModel(VeiculoProto proto) {

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(proto.getPlaca() != null ? proto.getPlaca() : "");
        veiculo.setQuilometragem(proto.getQuilometragem());
        veiculo.setCor(protoToModel(proto.getCor()));
        veiculo.setModelo(protoToModel(proto.getModelo()));
        return veiculo;
    }

    public static Cor protoToModel(CorProto proto) {
        Cor cor = new Cor();
        cor.setNome(proto.getNome() != null ? proto.getNome() : "");
        cor.setId(proto.getId());
        return cor;
    }

    public static Modelo protoToModel(ModeloProto proto) {

        Modelo modelo = new Modelo();
        modelo.setTipo(proto.getTipo() != null ? proto.getTipo() : "");
        modelo.setNome(proto.getNome() != null ? proto.getNome() : "");
        modelo.setFabricante(protoToModel(proto.getFabricante()));
        modelo.setId(proto.getId());
        return modelo;
    }

    public static AnoModelo protoToModel(AnoModeloProto proto) {

        AnoModelo anoModelo = new AnoModelo();
        anoModelo.setTipo(proto.getTipo() != null ? proto.getTipo() : "");
        anoModelo.setNome(proto.getNome() != null ? proto.getNome() : "");
        anoModelo.setId(proto.getId());
        anoModelo.setValor(proto.getValor());
        anoModelo.setModelo(protoToModel(proto.getModelo()));
        return anoModelo;
    }

    // CONVERSÃO DE CLASSES DE MODELO PARA PROTO
    public static FabricanteProto modelToProto(Fabricante model) {
        return FabricanteProto.newBuilder()
                .setId(model.getId())
                .setNome(model.getNome() != null ? model.getNome() : "")
                .build();
    }

    public static VeiculoProto modelToProto(Veiculo model) {
        return VeiculoProto.newBuilder()
                .setCor(modelToProto(model.getCor()))
                .setModelo(modelToProto(model.getModelo()))
                .setId(model.getId())
                .setQuilometragem(model.getQuilometragem())
                .setPlaca(model.getPlaca() != null ? model.getPlaca() : "")
                .build();
    }

    public static CorProto modelToProto(Cor model) {
        return CorProto.newBuilder()
                .setId(model.getId())
                .setNome(model.getNome() != null ? model.getNome() : "")
                .build();
    }

    public static ModeloProto modelToProto(Modelo model) {
        return ModeloProto.newBuilder()
                .setId(model.getId())
                .setNome(model.getNome() != null ? model.getNome() : "")
                .setTipo(model.getTipo() != null ? model.getTipo() : "")
                .setFabricante(modelToProto(model.getFabricante()))
                .build();
    }

    public static AnoModeloProto modelToProto(AnoModelo model) {
        return AnoModeloProto.newBuilder()
                .setId(model.getId())
                .setNome(model.getNome() != null ? model.getNome() : "")
                .setTipo(model.getTipo() != null ? model.getTipo() : "")
                .setModelo(modelToProto(model.getModelo()))
                .setValor(model.getValor())
                .build();
    }
}
