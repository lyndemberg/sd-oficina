package sd.oficina.shared.converter;

import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Nota;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.shared.proto.customer.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class ProtoConverterStore {

    public static EstoqueProto modelToProto(Estoque model) {
        return EstoqueProto.newBuilder()
                .setCodigoPeca(model.getCodigoPeca())
                .setIdPeca(model.getIdPeca())
                .setNomePeca(model.getNomePeca() != null ? model.getNomePeca() : "")
                .setQtdPeca(model.getQtdPeca())
                .setValorPeca(model.getValorPeca())
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
                .setId(model.getId())
                .setValor(model.getValor())
                .setEstoques(builder.build())
                .build();
    }

    public static Estoque protoToModel(EstoqueProto proto) {

        Estoque estoque = new Estoque();
        estoque.setIdPeca(proto.getIdPeca());
        estoque.setQtdPeca(proto.getQtdPeca());
        estoque.setValorPeca(proto.getValorPeca());
        estoque.setNomePeca(proto.getNomePeca() != null ? proto.getNomePeca() : "");
        estoque.setValidade(proto.getValidade() != null ? proto.getValidade() : "");
        estoque.setCodigoPeca(proto.getCodigoPeca());
        return estoque;
    }

    public static Servico protoToModel(ServicoProto proto) {
        Servico servico = new Servico();
        servico.setId(proto.getId());
        servico.setValor(proto.getValor());
        servico.setDescricao(proto.getDescricao() != null ? proto.getDescricao() : "");
        servico.setEstoques(proto.getEstoques().getEstoqueList().stream().map(e ->
                protoToModel(e)).collect(Collectors.toList()));
        return servico;
    }

    public static Nota protoToModel(NotaProto proto) {
        Nota nota = new Nota();
        nota.setDataCompra(LocalDate.parse(proto.getDataCompra()));
        nota.setDataVencimento(LocalDate.parse(proto.getDataVencimento()));
        nota.setEstoques(proto.getEstoques().getEstoqueList().stream().map(e ->
                protoToModel(e)).collect(Collectors.toList()));
        nota.setId(proto.getId());
        nota.setIdFornecedor(proto.getIdFornecedor());
        nota.setNumero(proto.getNumero());
        return nota;
    }

    public static NotaProto modelToProto(Nota model) {
        EstoqueProtoList.Builder builder = EstoqueProtoList.newBuilder();
        if (model.getEstoques() != null) {
            model.getEstoques().forEach(e -> builder.addEstoque(modelToProto(e)));
        }

        return NotaProto.newBuilder()
                .setDataCompra(model.getDataCompra().toString())
                .setDataVencimento(model.getDataVencimento().toString())
                .setEstoques(builder.build())
                .setId(model.getId())
                .setNumero(model.getNumero())
                .build();
    }
}
