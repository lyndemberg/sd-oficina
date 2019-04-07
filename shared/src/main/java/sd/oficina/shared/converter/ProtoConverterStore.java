package sd.oficina.shared.converter;

import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.shared.proto.customer.EstoqueProto;
import sd.oficina.shared.proto.customer.EstoqueProtoList;
import sd.oficina.shared.proto.customer.EstoqueProtoListOrBuilder;
import sd.oficina.shared.proto.customer.ServicoProto;

import java.util.stream.Collectors;

public class ProtoConverterStore {

    public static EstoqueProto modelToProto(Estoque model) {
        return EstoqueProto.newBuilder()
                .setCodigoPeca(model.getCodigoPeca() != 0 ? model.getCodigoPeca() : 0)
                .setIdPeca(model.getIdPeca() != 0 ? model.getIdPeca() : 0)
                .setNomePeca(model.getNomePeca() != null ? model.getNomePeca() : "")
                .setQtdPeca(model.getQtdPeca() != 0 ? model.getQtdPeca() : 0)
                .setValorPeca(model.getValorPeca() != 0 ? model.getValorPeca() : 0)
                .setValidade(model.getValidade() != null ? model.getValidade() : "")
                .build();
    }

    public static ServicoProto modelToProto(Servico model) {
        EstoqueProtoList.Builder builder = EstoqueProtoList.newBuilder();
        if (model.getEstoques() != null) {
            model.getEstoques().forEach(e -> builder.addEstoque(modelToProto(e)));
        }
        return ServicoProto.newBuilder()
                .setDescricao(model.getDescricao() != null ? model.getDescricao() : "")
                .setId(model.getId() != 0 ? model.getId() : 0)
                .setValor(model.getValor() != 0 ? model.getValor() : 0)
                .setEstoques(builder.build())
                .build();
    }

    public static Estoque protoToModel(EstoqueProto proto) {

        Estoque estoque = new Estoque();
        estoque.setIdPeca(proto.getIdPeca() != 0 ? proto.getIdPeca() : 0);
        estoque.setQtdPeca(proto.getQtdPeca() != 0 ? proto.getQtdPeca() : 0);
        estoque.setValorPeca(proto.getValorPeca() != 0 ? proto.getValorPeca() : 0);
        estoque.setNomePeca(proto.getNomePeca() != null ? proto.getNomePeca() : "");
        estoque.setValidade(proto.getValidade() != null ? proto.getValidade() : "");
        estoque.setCodigoPeca(proto.getCodigoPeca() != 0 ? proto.getCodigoPeca() : 0);
        return estoque;
    }

    public static Servico protoToModel(ServicoProto proto) {
        Servico servico = new Servico();
        servico.setId(proto.getId() != 0 ? proto.getId() : 0);
        servico.setValor(proto.getValor() != 0 ? proto.getValor() : 0);
        servico.setDescricao(proto.getDescricao() != null ? proto.getDescricao() : "");
        servico.setEstoques(proto.getEstoques().getEstoqueList().stream().map(e ->
                protoToModel(e)).collect(Collectors.toList()));
        return servico;
    }
}
