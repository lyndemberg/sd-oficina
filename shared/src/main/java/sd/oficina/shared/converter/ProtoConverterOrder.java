package sd.oficina.shared.converter;

import sd.oficina.shared.model.order.OrdemServico;
import sd.oficina.shared.proto.order.OrdemProto;
import sd.oficina.shared.util.LocalDateUtil;

public class ProtoConverterOrder {
    public static OrdemServico protoToModel(OrdemProto proto){
        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setId(proto.getIdPedido());
        ordemServico.setIdCliente(proto.getIdCliente());
        ordemServico.setIdVeiculo(proto.getIdVeiculo());
        ordemServico.setDataPagamento(LocalDateUtil.toLocalDate(proto.getDataPagamento()));
        ordemServico.setDataRegistro(LocalDateUtil.toLocalDate(proto.getDataRegistro()));
        ordemServico.setPago(proto.getPago());
        ordemServico.setConcluida(proto.getConcluido());
        ordemServico.setServicos(proto.getServicosList());
        return ordemServico;
    }

    public static OrdemProto modelToProto(OrdemServico ordemServico){
        return OrdemProto.newBuilder()
                .setConcluido(ordemServico.isConcluida())
                .setDataPagamento(LocalDateUtil.toString(ordemServico.getDataPagamento()))
                .setDataRegistro(LocalDateUtil.toString(ordemServico.getDataRegistro()))
                .setPago(ordemServico.isPago())
                .setIdCliente(ordemServico.getIdCliente())
                .setIdVeiculo(ordemServico.getIdVeiculo())
                .setIdPedido(ordemServico.getId())
                .addAllServicos(ordemServico.getServicos())
                .build();
    }
}
