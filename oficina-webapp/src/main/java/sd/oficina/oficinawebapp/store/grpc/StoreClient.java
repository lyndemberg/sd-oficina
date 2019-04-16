package sd.oficina.oficinawebapp.store.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.oficinawebapp.config.HostsProperties;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.shared.converter.ProtoConverterStore;
import sd.oficina.shared.model.store.Estoque;
import sd.oficina.shared.model.store.Nota;
import sd.oficina.shared.model.store.Servico;
import sd.oficina.shared.proto.customer.*;

import java.util.ArrayList;
import java.util.List;

public class StoreClient {
    private final HostsProperties hostsProperties;

    public StoreClient(HostsProperties hostsProperties) {
        this.hostsProperties = hostsProperties;
    }

    public Estoque salvarEstoque(Estoque estoque) throws FalhaGrpcException {
        Estoque salvo = null;
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            salvo = ProtoConverterStore
                    .protoToModel(EstoqueServiceGrpc.newBlockingStub(channelStore1)
                            .salvar(ProtoConverterStore.modelToProto(estoque))
                            .getEstoque());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try {
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                salvo = ProtoConverterStore
                        .protoToModel(EstoqueServiceGrpc.newBlockingStub(channelStore2)
                                .salvar(ProtoConverterStore.modelToProto(estoque))
                                .getEstoque());
            } catch (Exception ex) {
                throw new FalhaGrpcException();
            }

        }
        return salvo;
    }

    public Estoque atualizarEstoque(Estoque estoque) throws FalhaGrpcException {
        Estoque estoqueAtualizado = null;
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            estoqueAtualizado = ProtoConverterStore
                    .protoToModel(EstoqueServiceGrpc.newBlockingStub(channelStore1)
                            .atualizar(ProtoConverterStore.modelToProto(estoque))
                            .getEstoque());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                estoqueAtualizado = ProtoConverterStore
                        .protoToModel(EstoqueServiceGrpc.newBlockingStub(channelStore2)
                                .atualizar(ProtoConverterStore.modelToProto(estoque))
                                .getEstoque());
            }catch(Exception ex2){
                throw new FalhaGrpcException();
            }
        }

        return estoqueAtualizado;
    }

    public void deletarEstoque(int id) throws FalhaGrpcException {
        EstoqueProto proto = EstoqueProto.newBuilder().setIdPeca(id).build();
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            EstoqueServiceGrpc.newBlockingStub(channelStore1).deletar(proto);
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                EstoqueServiceGrpc.newBlockingStub(channelStore2).deletar(proto);
            }catch(Exception ex2){
                throw new FalhaGrpcException();
            }
        }
    }

    public Estoque buscarEstoque(int id) throws FalhaGrpcException {
        Estoque estoque = null;
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            estoque = ProtoConverterStore
                    .protoToModel(EstoqueServiceGrpc.newBlockingStub(channelStore1)
                            .buscar(EstoqueProto.newBuilder()
                                    .setIdPeca(id).build())
                            .getEstoque());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                estoque = ProtoConverterStore
                        .protoToModel(EstoqueServiceGrpc.newBlockingStub(channelStore2)
                                .buscar(EstoqueProto.newBuilder()
                                        .setIdPeca(id).build())
                                .getEstoque());
            }catch(Exception ex2) {
                throw new FalhaGrpcException();
            }

        }

        return estoque;
    }

    public List<Estoque> buscarTodosEstoque() throws FalhaGrpcException {
        List<Estoque> estoques = new ArrayList<>();
        List<EstoqueProto> estoqueProtoList = new ArrayList<>();
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            estoqueProtoList = EstoqueServiceGrpc
                    .newBlockingStub(channelStore1)
                    .buscarTodos(Empty.newBuilder().build())
                    .getEstoqueList();
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                estoqueProtoList = EstoqueServiceGrpc
                        .newBlockingStub(channelStore2)
                        .buscarTodos(Empty.newBuilder().build())
                        .getEstoqueList();
            }catch(Exception ex2){
                throw new FalhaGrpcException();
            }
        }

        estoqueProtoList.forEach(p -> estoques.add(ProtoConverterStore.protoToModel(p)));

        return estoques;
    }

    public List<Servico> todosServicos() throws FalhaGrpcException {
        List<Servico> servicos = new ArrayList<>();
        List<ServicoProto> servicoProtoList = new ArrayList<>();
        Empty empty = Empty.newBuilder().build();
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore1);
            servicoProtoList = stub.buscarTodos(empty).getServicoList();
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore2);
                servicoProtoList = stub.buscarTodos(empty).getServicoList();
            }catch(Exception ex2){
                throw new FalhaGrpcException();
            }
        }

        servicoProtoList.forEach(p -> servicos.add(ProtoConverterStore.protoToModel(p)));

        return servicos;
    }

    public Servico salvarServico(Servico Servico) throws FalhaGrpcException {
        ServicoProto proto = ProtoConverterStore.modelToProto(Servico);
        Servico salvo = null;
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();

            ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore1);
            salvo = ProtoConverterStore.protoToModel(stub.salvar(proto).getServico());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try {
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore2);
                salvo = ProtoConverterStore.protoToModel(stub.salvar(proto).getServico());
            } catch (Exception ex) {
                throw new FalhaGrpcException();
            }

        }
        return salvo;
    }

    public Servico buscarServico(Long id) throws FalhaGrpcException {
        Servico servico = null;
        ServicoProto proto = ServicoProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore1);
            servico = ProtoConverterStore.protoToModel(stub.buscar(proto).getServico());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore2);
                servico = ProtoConverterStore.protoToModel(stub.buscar(proto).getServico());
            }catch(Exception ex2) {
                throw new FalhaGrpcException();
            }

        }

        return servico;
    }

    public Servico atualizarServico(Servico Servico) throws FalhaGrpcException {
        Servico servicoAtualizado = null;
        ServicoProto servicoProto = ProtoConverterStore.modelToProto(Servico);
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore1);
            servicoAtualizado = ProtoConverterStore
                    .protoToModel(stub
                            .atualizar(servicoProto).getServico());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore2);
                servicoAtualizado = ProtoConverterStore
                        .protoToModel(stub
                                .atualizar(servicoProto).getServico());
            }catch(Exception ex2){
                throw new FalhaGrpcException();
            }
        }

        return servicoAtualizado;
    }

    public void deletarServico(Long id) throws FalhaGrpcException {
        ServicoProto proto = ServicoProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore1);
            stub.deletar(proto);
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                ServicoServiceGrpc.ServicoServiceBlockingStub stub = ServicoServiceGrpc.newBlockingStub(channelStore2);
                stub.deletar(proto);
            }catch(Exception ex2){
                throw new FalhaGrpcException();
            }
        }
    }

    public Nota salvarNota(Nota nota) throws FalhaGrpcException {
        Nota salvo = null;
        NotaProto notaProto = ProtoConverterStore.modelToProto(nota);
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            salvo = ProtoConverterStore
                    .protoToModel(NotaServiceGrpc.newBlockingStub(channelStore1)
                            .salvar(notaProto)
                            .getNota());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try {
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                salvo = ProtoConverterStore
                        .protoToModel(NotaServiceGrpc.newBlockingStub(channelStore2)
                                .salvar(notaProto)
                                .getNota());
            } catch (Exception ex) {
                throw new FalhaGrpcException();
            }

        }
        return salvo;

    }

    public Nota atualizarNota(Nota nota) throws FalhaGrpcException {
        Nota notaAtualizado = null;
        NotaProto notaProto = ProtoConverterStore.modelToProto(nota);
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            notaAtualizado = ProtoConverterStore
                    .protoToModel(NotaServiceGrpc.newBlockingStub(channelStore1)
                            .atualizar(notaProto)
                            .getNota());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                notaAtualizado = ProtoConverterStore
                        .protoToModel(NotaServiceGrpc.newBlockingStub(channelStore2)
                                .atualizar(notaProto)
                                .getNota());
            }catch(Exception ex2){
                throw new FalhaGrpcException();
            }
        }

        return notaAtualizado;
    }

    public Nota buscarNota(int id) throws FalhaGrpcException {
        Nota nota = null;
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            nota =  ProtoConverterStore
                    .protoToModel(NotaServiceGrpc.newBlockingStub(channelStore1)
                            .buscar(NotaProto.newBuilder()
                                    .setId(id).build())
                            .getNota());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                nota =  ProtoConverterStore
                        .protoToModel(NotaServiceGrpc.newBlockingStub(channelStore2)
                                .buscar(NotaProto.newBuilder()
                                        .setId(id).build())
                                .getNota());
            }catch(Exception ex2) {
                throw new FalhaGrpcException();
            }

        }

        return nota;
    }

    public List<Nota> todasNotas() throws FalhaGrpcException {
        List<Nota> notas = new ArrayList<>();
        List<NotaProto> notaProtoList = new ArrayList<>();
        Empty empty = Empty.newBuilder().build();
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            notaProtoList = NotaServiceGrpc
                    .newBlockingStub(channelStore1)
                    .buscarTodos(Empty.newBuilder().build())
                    .getNotaList();
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                notaProtoList = NotaServiceGrpc
                        .newBlockingStub(channelStore2)
                        .buscarTodos(Empty.newBuilder().build())
                        .getNotaList();
            }catch(Exception ex2){
                throw new FalhaGrpcException();
            }
        }


        notaProtoList.forEach(p -> notas.add(ProtoConverterStore.protoToModel(p)));

        return notas;
    }

    public void deletarNota(long id) throws FalhaGrpcException {
        NotaProto proto = NotaProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM STORE 1
            ManagedChannel channelStore1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getStore1Host(), hostsProperties.getStore1Port())
                    .usePlaintext()
                    .build();
            NotaServiceGrpc.newBlockingStub(channelStore1).deletar(NotaProto.newBuilder().setId(id).build());
        }catch(Exception ex1){
            //TENTANDO COM STORE 2
            try{
                ManagedChannel channelStore2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getStore2Host(), hostsProperties.getStore2Port())
                        .usePlaintext()
                        .build();
                NotaServiceGrpc.newBlockingStub(channelStore2).deletar(NotaProto.newBuilder().setId(id).build());
            }catch(Exception ex2){
                throw new FalhaGrpcException();
            }
        }
    }
}
