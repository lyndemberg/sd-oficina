package sd.oficina.oficinawebapp.person.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import sd.oficina.oficinawebapp.config.HostsProperties;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.person.Cidade;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.model.person.Estado;
import sd.oficina.shared.model.person.Fornecedor;
import sd.oficina.shared.proto.person.*;

import java.util.ArrayList;
import java.util.List;

public class PersonClient {
    private final HostsProperties hostsProperties;

    public PersonClient(HostsProperties hostsProperties) {
        this.hostsProperties = hostsProperties;
    }

    public Cliente salvar (Cliente cliente) throws FalhaGrpcException {
        Cliente clientePersistido = null;
        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            ClienteProto clienteProto =
                    ClienteServiceGrpc.
                            newBlockingStub(channelPerson1)
                            .salvar(ProtoConverterPerson
                                    .modelToProto(cliente))
                            .getCliente();
            clientePersistido = ProtoConverterPerson.protoToModel(clienteProto);
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                ClienteProto clienteProto =
                        ClienteServiceGrpc.
                                newBlockingStub(channelPerson2)
                                .salvar(ProtoConverterPerson
                                        .modelToProto(cliente))
                                .getCliente();
                clientePersistido = ProtoConverterPerson.protoToModel(clienteProto);
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    ClienteProto clienteProto =
                            ClienteServiceGrpc.
                                    newBlockingStub(channelPerson3)
                                    .salvar(ProtoConverterPerson
                                            .modelToProto(cliente))
                                    .getCliente();
                    clientePersistido = ProtoConverterPerson.protoToModel(clienteProto);
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return clientePersistido;
    }

    public Cliente atualizar(Cliente cliente) throws FalhaGrpcException {
        Cliente clienteAtualizado = null;
        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            clienteAtualizado =
                    ProtoConverterPerson
                            .protoToModel(ClienteServiceGrpc
                                    .newBlockingStub(channelPerson1)
                                    .atualizar(ProtoConverterPerson
                                            .modelToProto(cliente))
                                    .getCliente());
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                clienteAtualizado =
                        ProtoConverterPerson
                                .protoToModel(ClienteServiceGrpc
                                        .newBlockingStub(channelPerson2)
                                        .atualizar(ProtoConverterPerson
                                                .modelToProto(cliente))
                                        .getCliente());
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    clienteAtualizado =
                            ProtoConverterPerson
                                    .protoToModel(ClienteServiceGrpc
                                            .newBlockingStub(channelPerson3)
                                            .atualizar(ProtoConverterPerson
                                                    .modelToProto(cliente))
                                            .getCliente());
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return clienteAtualizado;
    }

    public void deletar(Long idDoCliente) throws FalhaGrpcException {
        try{
            //TENTANDO SALVAR COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            ClienteServiceGrpc
                    .newBlockingStub(channelPerson1)
                    .deletar(ClienteProto
                            .newBuilder().setId(idDoCliente)
                            .build());
        }catch(StatusRuntimeException ex1){
            //TENTANDO SALVAR COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                ClienteServiceGrpc
                        .newBlockingStub(channelPerson2)
                        .deletar(ClienteProto
                                .newBuilder().setId(idDoCliente)
                                .build());
            }catch(StatusRuntimeException ex2){
                //TENTANDO SALVAR COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    ClienteServiceGrpc
                            .newBlockingStub(channelPerson3)
                            .deletar(ClienteProto
                                    .newBuilder().setId(idDoCliente)
                                    .build());
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }
    }

    public Cliente buscarCliente(Long idDoCliente) throws FalhaGrpcException {
        Cliente cliente = null;
        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            cliente = ProtoConverterPerson
                    .protoToModel(ClienteServiceGrpc
                            .newBlockingStub(channelPerson1)
                            .buscar(ClienteProto
                                    .newBuilder()
                                    .setId(idDoCliente)
                                    .build()).getCliente());
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                cliente = ProtoConverterPerson
                        .protoToModel(ClienteServiceGrpc
                                .newBlockingStub(channelPerson2)
                                .buscar(ClienteProto
                                        .newBuilder()
                                        .setId(idDoCliente)
                                        .build()).getCliente());
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    cliente = ProtoConverterPerson
                            .protoToModel(ClienteServiceGrpc
                                    .newBlockingStub(channelPerson3)
                                    .buscar(ClienteProto
                                            .newBuilder()
                                            .setId(idDoCliente)
                                            .build()).getCliente());
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return cliente;
    }

    public List<Cliente> listarClientes() throws FalhaGrpcException {
        List<Cliente> clientes = new ArrayList<>();
        List<ClienteProto> clientesProto = new ArrayList<>();
        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            clientesProto =
                    ClienteServiceGrpc
                            .newBlockingStub(channelPerson1)
                            .listar(Empty.newBuilder()
                                    .build()).getClientesList();
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                clientesProto =
                        ClienteServiceGrpc
                                .newBlockingStub(channelPerson2)
                                .listar(Empty.newBuilder()
                                        .build()).getClientesList();
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    clientesProto =
                            ClienteServiceGrpc
                                    .newBlockingStub(channelPerson3)
                                    .listar(Empty.newBuilder()
                                            .build()).getClientesList();
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        for (ClienteProto cliente: clientesProto) {
            clientes.add(ProtoConverterPerson.protoToModel(cliente));
        }

        return clientes;
    }

    public Cidade buscarCidade(int idDaCidade) throws FalhaGrpcException {
        Cidade cidade = null;

        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            cidade = ProtoConverterPerson
                    .protoToModel(CidadeServiceGrpc
                            .newBlockingStub(channelPerson1)
                            .buscar(CidadeProto
                                    .newBuilder()
                                    .setId(idDaCidade)
                                    .build()).getCidade());
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                cidade = ProtoConverterPerson
                        .protoToModel(CidadeServiceGrpc
                                .newBlockingStub(channelPerson2)
                                .buscar(CidadeProto
                                        .newBuilder()
                                        .setId(idDaCidade)
                                        .build()).getCidade());
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    cidade = ProtoConverterPerson
                            .protoToModel(CidadeServiceGrpc
                                    .newBlockingStub(channelPerson3)
                                    .buscar(CidadeProto
                                            .newBuilder()
                                            .setId(idDaCidade)
                                            .build()).getCidade());
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return cidade;
    }

    public List<Cidade> listarCidades() throws FalhaGrpcException {
        List<Cidade> cidades = new ArrayList<>();
        List<CidadeProto> cidadesProto = new ArrayList<>();
        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            cidadesProto =
                    CidadeServiceGrpc
                            .newBlockingStub(channelPerson1)
                            .listar(Empty.newBuilder()
                                    .build()).getCidadesList();
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                cidadesProto =
                        CidadeServiceGrpc
                                .newBlockingStub(channelPerson2)
                                .listar(Empty.newBuilder()
                                        .build()).getCidadesList();
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    cidadesProto =
                            CidadeServiceGrpc
                                    .newBlockingStub(channelPerson3)
                                    .listar(Empty.newBuilder()
                                            .build()).getCidadesList();
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        for (CidadeProto cidade: cidadesProto) {
            cidades.add(ProtoConverterPerson.protoToModel(cidade));
        }

        return cidades;
    }

    public Estado buscarEstado(int idDoEstado) throws FalhaGrpcException {
        Estado estado = null;

        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            estado = ProtoConverterPerson
                    .protoToModel(EstadoServiceGrpc
                            .newBlockingStub(channelPerson1)
                            .buscar(EstadoProto
                                    .newBuilder()
                                    .setId(idDoEstado)
                                    .build()).getEstado());
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                estado = ProtoConverterPerson
                        .protoToModel(EstadoServiceGrpc
                                .newBlockingStub(channelPerson2)
                                .buscar(EstadoProto
                                        .newBuilder()
                                        .setId(idDoEstado)
                                        .build()).getEstado());
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    estado = ProtoConverterPerson
                            .protoToModel(EstadoServiceGrpc
                                    .newBlockingStub(channelPerson3)
                                    .buscar(EstadoProto
                                            .newBuilder()
                                            .setId(idDoEstado)
                                            .build()).getEstado());
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return estado;
    }

    public List<Estado> listarEstados() throws FalhaGrpcException {
        List<Estado> estados = new ArrayList<>();
        List<EstadoProto> estadosProto = new ArrayList<>();

        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            estadosProto =
                    EstadoServiceGrpc
                            .newBlockingStub(channelPerson1)
                            .listar(Empty.newBuilder()
                                    .build()).getEstadosList();
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                estadosProto =
                        EstadoServiceGrpc
                                .newBlockingStub(channelPerson2)
                                .listar(Empty.newBuilder()
                                        .build()).getEstadosList();
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    estadosProto =
                            EstadoServiceGrpc
                                    .newBlockingStub(channelPerson3)
                                    .listar(Empty.newBuilder()
                                            .build()).getEstadosList();
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }


        for (EstadoProto estado: estadosProto) {
            estados.add(ProtoConverterPerson.protoToModel(estado));
        }

        return estados;
    }

    public Fornecedor salvar (Fornecedor fornecedor) throws FalhaGrpcException {
        Fornecedor fornecedorPersistido = null;
        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            FornecedorProto fornecedorProto =
                    FornecedorServiceGrpc.
                            newBlockingStub(channelPerson1)
                            .salvar(ProtoConverterPerson
                                    .modelToProto(fornecedor))
                            .getFornecedor();

            fornecedorPersistido = ProtoConverterPerson.protoToModel(fornecedorProto);
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                FornecedorProto fornecedorProto =
                        FornecedorServiceGrpc.
                                newBlockingStub(channelPerson2)
                                .salvar(ProtoConverterPerson
                                        .modelToProto(fornecedor))
                                .getFornecedor();

                fornecedorPersistido = ProtoConverterPerson.protoToModel(fornecedorProto);
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    FornecedorProto fornecedorProto =
                            FornecedorServiceGrpc.
                                    newBlockingStub(channelPerson3)
                                    .salvar(ProtoConverterPerson
                                            .modelToProto(fornecedor))
                                    .getFornecedor();

                    fornecedorPersistido = ProtoConverterPerson.protoToModel(fornecedorProto);
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return fornecedorPersistido;
    }

    public Fornecedor atualizar(Fornecedor fornecedor) throws FalhaGrpcException {
        Fornecedor fornecedorAtualizado = null;
        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            fornecedorAtualizado =
                    ProtoConverterPerson
                            .protoToModel(FornecedorServiceGrpc
                                    .newBlockingStub(channelPerson1)
                                    .atualizar(ProtoConverterPerson
                                            .modelToProto(fornecedor))
                                    .getFornecedor());
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                fornecedorAtualizado =
                        ProtoConverterPerson
                                .protoToModel(FornecedorServiceGrpc
                                        .newBlockingStub(channelPerson2)
                                        .atualizar(ProtoConverterPerson
                                                .modelToProto(fornecedor))
                                        .getFornecedor());
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    fornecedorAtualizado =
                            ProtoConverterPerson
                                    .protoToModel(FornecedorServiceGrpc
                                            .newBlockingStub(channelPerson3)
                                            .atualizar(ProtoConverterPerson
                                                    .modelToProto(fornecedor))
                                            .getFornecedor());
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return fornecedorAtualizado;
    }

    public void deletar(int idDoFornecedor) throws FalhaGrpcException {
        try{
            //TENTANDO SALVAR COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            FornecedorServiceGrpc
                    .newBlockingStub(channelPerson1)
                    .deletar(FornecedorProto
                            .newBuilder().setId(idDoFornecedor)
                            .build());
        }catch(StatusRuntimeException ex1){
            //TENTANDO SALVAR COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                FornecedorServiceGrpc
                        .newBlockingStub(channelPerson2)
                        .deletar(FornecedorProto
                                .newBuilder().setId(idDoFornecedor)
                                .build());
            }catch(StatusRuntimeException ex2){
                //TENTANDO SALVAR COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    FornecedorServiceGrpc
                            .newBlockingStub(channelPerson3)
                            .deletar(FornecedorProto
                                    .newBuilder().setId(idDoFornecedor)
                                    .build());
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }
    }

    public Fornecedor buscar(int idDoFornecedor) throws FalhaGrpcException {
        Fornecedor fornecedor = null;
        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            fornecedor = ProtoConverterPerson
                    .protoToModel(FornecedorServiceGrpc
                            .newBlockingStub(channelPerson1)
                            .buscar(FornecedorProto
                                    .newBuilder()
                                    .setId(idDoFornecedor)
                                    .build()).getFornecedor());
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                fornecedor = ProtoConverterPerson
                        .protoToModel(FornecedorServiceGrpc
                                .newBlockingStub(channelPerson2)
                                .buscar(FornecedorProto
                                        .newBuilder()
                                        .setId(idDoFornecedor)
                                        .build()).getFornecedor());
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    fornecedor = ProtoConverterPerson
                            .protoToModel(FornecedorServiceGrpc
                                    .newBlockingStub(channelPerson3)
                                    .buscar(FornecedorProto
                                            .newBuilder()
                                            .setId(idDoFornecedor)
                                            .build()).getFornecedor());
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return fornecedor;
    }

    public List<Fornecedor> listar() throws FalhaGrpcException {
        List<Fornecedor> fornecedores = new ArrayList<>();
        List<FornecedorProto> fornecedoresProto = new ArrayList<>();

        try{
            //TENTANDO COM PERSON1
            ManagedChannel channelPerson1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getPerson1Host(), hostsProperties.getPerson1Port())
                    .usePlaintext()
                    .build();
            fornecedoresProto =
                    FornecedorServiceGrpc
                            .newBlockingStub(channelPerson1)
                            .listar(Empty.newBuilder()
                                    .build()).getFornecedoresList();
        }catch(StatusRuntimeException ex1){
            //TENTANDO COM PERSON2
            try{
                ManagedChannel channelPerson2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getPerson2Host(), hostsProperties.getPerson2Port())
                        .usePlaintext()
                        .build();
                fornecedoresProto =
                        FornecedorServiceGrpc
                                .newBlockingStub(channelPerson2)
                                .listar(Empty.newBuilder()
                                        .build()).getFornecedoresList();
            }catch(StatusRuntimeException ex2){
                //TENTANDO COM PERSON3
                try{
                    ManagedChannel channelPerson3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getPerson3Host(), hostsProperties.getPerson3Port())
                            .usePlaintext()
                            .build();
                    fornecedoresProto =
                            FornecedorServiceGrpc
                                    .newBlockingStub(channelPerson3)
                                    .listar(Empty.newBuilder()
                                            .build()).getFornecedoresList();
                }catch(StatusRuntimeException ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        for (FornecedorProto fornecedor: fornecedoresProto) {
            fornecedores.add(ProtoConverterPerson.protoToModel(fornecedor));
        }

        return fornecedores;
    }
}
