package sd.oficina.shared.converter;

import sd.oficina.shared.model.person.*;
import sd.oficina.shared.proto.person.*;

public class ProtoConverterPerson {

    // CONVERSÃO DE PROTOS PARA CLASSES DE MODELO
    public static Cidade protoToModel(CidadeProto proto) {
        Cidade cidade = new Cidade();
        cidade.setId(proto.getId());
        cidade.setNome(proto.getNome() != null ? proto.getNome() : "");
        cidade.setEstado(protoToModel(proto.getEstado()));
        return cidade;
    }

    public static Estado protoToModel(EstadoProto proto) {
        Estado estado = new Estado();
        estado.setId(proto.getId());
        estado.setNome(proto.getNome() != null ? proto.getNome() : "");
        return estado;
    }

    public static Cliente protoToModel(ClienteProto proto) {
        Cliente cliente = new Cliente();
        cliente.setId(proto.getId());
        cliente.setTelefoneFixo(proto.getTelefoneFixo() != null ? proto.getTelefoneFixo() : "");
        cliente.setCEP(proto.getCEP() != null ? proto.getCEP() : "");
        cliente.setNumero(proto.getNumero() != 0 ? proto.getNumero() : 0);
        cliente.setLogradouro(proto.getLogradouro() != null ? proto.getLogradouro() : "");
        cliente.setBairro(proto.getBairro() != null ? proto.getBairro() : "");
        cliente.setCPF(proto.getCPF() != null ? proto.getCPF() : "");
        cliente.setComplemento(proto.getComplemento() != null ? proto.getComplemento() : "");
        cliente.setNome(proto.getNome() != null ? proto.getNome() : "");
        cliente.setTelefoneCelular(proto.getTelefoneCelular() != null ? proto.getTelefoneCelular() : "");
        cliente.setEmail(proto.getEmail() != null ? proto.getEmail() : "");
        cliente.setEstado(protoToModel(proto.getEstado()));
        cliente.setCidade(protoToModel(proto.getCidade()));
        return cliente;
    }

    public static Fornecedor protoToModel(FornecedorProto proto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(proto.getId());
        fornecedor.setNomeFantasia(proto.getNomeFantasia() != null ? proto.getNomeFantasia() : "");
        fornecedor.setRazaoSocial(proto.getRazaoSocial() != null ? proto.getRazaoSocial() : "");
        fornecedor.setVendedor(proto.getVendedor() != null ? proto.getVendedor() : "");
        fornecedor.setCodigo(proto.getCodigo() != 0 ? proto.getCodigo() : 0);
        fornecedor.setCNPJ(proto.getCNPJ() != null ? proto.getCNPJ() : "");
        fornecedor.setLogradouro(proto.getLogradouro() != null ? proto.getLogradouro() : "");
        fornecedor.setNumero(proto.getNumero() != 0 ? proto.getNumero() : 0);
        fornecedor.setTelefone(proto.getTelefone() != null ? proto.getTelefone() : "");
        fornecedor.setComplemento(proto.getComplemento() != null ? proto.getComplemento() : "");
        fornecedor.setBairro(proto.getBairro() != null ? proto.getBairro() : "");
        fornecedor.setCEP(proto.getCEP() != null ? proto.getCEP() : "");
        fornecedor.setCidade(protoToModel(proto.getCidade()));
        fornecedor.setEstado(protoToModel(proto.getEstado()));
        return fornecedor;
    }

    // CONVERSÃO DE CLASSES DE MODELO PARA PROTO
    public static CidadeProto modelToProto(Cidade model) {
        return CidadeProto.newBuilder()
                .setId(model.getId())
                .setNome(model.getNome() != null ? model.getNome() : "")
                .setEstado(modelToProto(model.getEstado()))
                .build();
    }

    public static EstadoProto modelToProto(Estado model) {
        return EstadoProto.newBuilder()
                .setId(model.getId())
                .setNome(model.getNome() != null ? model.getNome() : "")
                .build();
    }

    public static FornecedorProto modelToProto(Fornecedor model) {
        return FornecedorProto.newBuilder()
                .setId(model.getId())
                .setNomeFantasia(model.getNomeFantasia() != null ? model.getNomeFantasia() : "")
                .setRazaoSocial(model.getRazaoSocial() != null ? model.getRazaoSocial() : "")
                .setVendedor(model.getVendedor() != null ? model.getVendedor() : "")
                .setCodigo(model.getCodigo())
                .setCNPJ(model.getCNPJ() != null ? model.getCNPJ() : "")
                .setLogradouro(model.getLogradouro() != null ? model.getLogradouro() : "")
                .setNumero(model.getNumero() != 0 ? model.getNumero() : 0)
                .setTelefone(model.getTelefone() != null ? model.getTelefone() : "")
                .setComplemento(model.getComplemento() != null ? model.getComplemento() : "")
                .setBairro(model.getBairro() != null ? model.getBairro() : "")
                .setCEP(model.getCEP() != null ? model.getCEP() : "")
                .setCidade(modelToProto(model.getCidade()))
                .setEstado(modelToProto(model.getEstado()))
                .build();
    }

    public static ClienteProto modelToProto(Cliente model) {
        return ClienteProto.newBuilder()
                .setId(model.getId())
                .setTelefoneFixo(model.getTelefoneFixo() != null ? model.getTelefoneFixo() : "")
                .setCEP(model.getCEP() != null ? model.getCEP() : "")
                .setNumero(model.getNumero() != 0 ? model.getNumero() : 0)
                .setLogradouro(model.getLogradouro() != null ? model.getLogradouro() : "")
                .setBairro(model.getBairro() != null ? model.getBairro() : "")
                .setCPF(model.getCPF() != null ? model.getCPF() : "")
                .setComplemento(model.getComplemento() != null ? model.getComplemento() : "")
                .setNome(model.getNome() != null ? model.getNome() : "")
                .setTelefoneCelular(model.getTelefoneCelular() != null ? model.getTelefoneCelular() : "")
                .setEmail(model.getEmail() != null ? model.getEmail() : "")
                .setEstado(modelToProto(model.getEstado()))
                .setCidade(modelToProto(model.getCidade()))
                .build();
    }
}
