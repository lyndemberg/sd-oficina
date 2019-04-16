package sd.oficina.oficinawebapp.customer.grpc;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sd.oficina.oficinawebapp.config.HostsProperties;
import sd.oficina.oficinawebapp.exception.FalhaGrpcException;
import sd.oficina.shared.converter.ProtoConverterCustomer;
import sd.oficina.shared.converter.ProtoConverterPerson;
import sd.oficina.shared.model.customer.AnoModelo;
import sd.oficina.shared.model.customer.Fabricante;
import sd.oficina.shared.model.customer.Modelo;
import sd.oficina.shared.model.customer.Veiculo;
import sd.oficina.shared.model.person.Cliente;
import sd.oficina.shared.proto.customer.*;
import sd.oficina.shared.proto.person.ClienteProto;
import sd.oficina.shared.proto.person.ClienteServiceGrpc;
import sd.oficina.shared.proto.person.FornecedorProto;
import sd.oficina.shared.proto.person.FornecedorServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class CustomerClient {
    private final HostsProperties hostsProperties;

    public CustomerClient(HostsProperties hostsProperties) {
        this.hostsProperties = hostsProperties;
    }

    public AnoModelo salvarAnoModelo(AnoModelo anoModelo) throws FalhaGrpcException {
        AnoModelo persistido = null;
        AnoModeloProto grpc = ProtoConverterCustomer.modelToProto(anoModelo);
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            AnoModeloProto proto =  AnoModeloServiceGrpc
                    .newBlockingStub(channelCustomer1)
                    .salvar(ProtoConverterCustomer.modelToProto(anoModelo))
                    .getAnoModelo();
            persistido = ProtoConverterCustomer.protoToModel(proto);
        }catch(Exception ex1){
            //TENTANDO COM CUSTOMER2
            try{
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                AnoModeloProto proto =  AnoModeloServiceGrpc
                        .newBlockingStub(channelCustomer2)
                        .salvar(ProtoConverterCustomer.modelToProto(anoModelo))
                        .getAnoModelo();
                persistido = ProtoConverterCustomer.protoToModel(proto);
            }catch(Exception ex2){
                //TENTANDO COM CUSTOMER3
                try{
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    AnoModeloProto proto =  AnoModeloServiceGrpc
                            .newBlockingStub(channelCustomer3)
                            .salvar(ProtoConverterCustomer.modelToProto(anoModelo))
                            .getAnoModelo();
                    persistido = ProtoConverterCustomer.protoToModel(proto);
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return persistido;
    }

    public AnoModelo buscarAnoModelo(int id) throws FalhaGrpcException {
        AnoModelo anoModelo = null;
        AnoModeloProto proto = AnoModeloProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channelCustomer1);
            anoModelo = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getAnoModelo());
        }catch(Exception ex1){
            //TENTANDO COM CUSTOMER2
            try{
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channelCustomer2);
                anoModelo = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getAnoModelo());
            }catch(Exception ex2){
                //TENTANDO COM CUSTOMER3
                try{
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channelCustomer3);
                    anoModelo = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getAnoModelo());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return anoModelo;
    }

    public void deletarAnoModelo(int id) throws FalhaGrpcException {
        AnoModeloProto proto = AnoModeloProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            AnoModeloServiceGrpc.newBlockingStub(channelCustomer1).deletar(proto);
        }catch(Exception ex1){

            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                AnoModeloServiceGrpc.newBlockingStub(channelCustomer2).deletar(proto);
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    AnoModeloServiceGrpc.newBlockingStub(channelCustomer3).deletar(proto);
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

    }

    public AnoModelo atualizarAnoModelo(AnoModelo anoModelo) throws FalhaGrpcException {
        AnoModelo anoModeloAtualizado = null;
        AnoModeloProto proto = ProtoConverterCustomer.modelToProto(anoModelo);
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channelCustomer1);
            anoModeloAtualizado = ProtoConverterCustomer
                    .protoToModel(stub
                            .atualizar(proto).getAnoModelo());
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channelCustomer2);
                anoModeloAtualizado = ProtoConverterCustomer
                        .protoToModel(stub
                                .atualizar(proto).getAnoModelo());
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    AnoModeloServiceGrpc.AnoModeloServiceBlockingStub stub = AnoModeloServiceGrpc.newBlockingStub(channelCustomer3);
                    anoModeloAtualizado = ProtoConverterCustomer
                            .protoToModel(stub
                                    .atualizar(proto).getAnoModelo());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return anoModeloAtualizado;
    }

    public List<AnoModelo> listarAnoModelo() throws FalhaGrpcException {
        List<AnoModelo> result = new ArrayList<>();
        List<AnoModeloProto> fornecedoresProto = new ArrayList<>();
        Empty empty = Empty.newBuilder().build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            fornecedoresProto = AnoModeloServiceGrpc.newBlockingStub(channelCustomer1).buscarTodos(empty).getAnoModelosList();
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                fornecedoresProto = AnoModeloServiceGrpc.newBlockingStub(channelCustomer2).buscarTodos(empty).getAnoModelosList();
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    fornecedoresProto = AnoModeloServiceGrpc.newBlockingStub(channelCustomer3).buscarTodos(empty).getAnoModelosList();
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        fornecedoresProto.forEach(f-> result.add(ProtoConverterCustomer.protoToModel(f)));

        return result;
    }

    public List<Fabricante> todosFabricantes() throws FalhaGrpcException {
        List<Fabricante> result = new ArrayList<>();
        List<FabricanteProto> fabricanteProtos = new ArrayList<>();
        Empty empty = Empty.newBuilder().build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer1);
            fabricanteProtos = stub.buscarTodos(empty).getFabricantesList();
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer2);
                fabricanteProtos = stub.buscarTodos(empty).getFabricantesList();
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer3);
                    fabricanteProtos = stub.buscarTodos(empty).getFabricantesList();
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        fabricanteProtos.forEach(f-> result.add(ProtoConverterCustomer.protoToModel(f)));

        return result;
    }

    public Fabricante salvarFabricante(Fabricante fabricante) throws FalhaGrpcException {
        Fabricante persistido = null;
        FabricanteProto grpc = ProtoConverterCustomer.modelToProto(fabricante);
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer1);
            persistido = ProtoConverterCustomer
                    .protoToModel(stub
                            .salvar(ProtoConverterCustomer.modelToProto(fabricante))
                            .getFabricante());
        }catch(Exception ex1){
            //TENTANDO COM CUSTOMER2
            try{
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer2);
                persistido = ProtoConverterCustomer
                        .protoToModel(stub
                                .salvar(ProtoConverterCustomer.modelToProto(fabricante))
                                .getFabricante());
            }catch(Exception ex2){
                //TENTANDO COM CUSTOMER3
                try{
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer3);
                    persistido = ProtoConverterCustomer
                            .protoToModel(stub
                                    .salvar(ProtoConverterCustomer.modelToProto(fabricante))
                                    .getFabricante());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        return persistido;
    }

    public Fabricante buscarFabricante(int id) throws FalhaGrpcException {
        Fabricante fabricante = null;
        FabricanteProto proto = FabricanteProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer1);
            fabricante = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getFabricante());
        }catch(Exception ex1){
            //TENTANDO COM CUSTOMER2
            try{
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer2);
                fabricante = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getFabricante());
            }catch(Exception ex2){
                //TENTANDO COM CUSTOMER3
                try{
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer3);
                    fabricante = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getFabricante());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return fabricante;
    }

    public Fabricante atualizarFabricante(Fabricante fabricante) throws FalhaGrpcException {
        Fabricante fabricanteAtualizado = null;
        FabricanteProto proto = ProtoConverterCustomer.modelToProto(fabricante);
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer1);
            fabricanteAtualizado = ProtoConverterCustomer
                    .protoToModel(stub
                            .atualizar(ProtoConverterCustomer.modelToProto(fabricante)).getFabricante());
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer2);
                fabricanteAtualizado = ProtoConverterCustomer
                        .protoToModel(stub
                                .atualizar(ProtoConverterCustomer.modelToProto(fabricante)).getFabricante());
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer3);
                    fabricanteAtualizado = ProtoConverterCustomer
                            .protoToModel(stub
                                    .atualizar(ProtoConverterCustomer.modelToProto(fabricante)).getFabricante());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        return fabricanteAtualizado;
    }

    public void deletarFabricante(int id) throws FalhaGrpcException {
        FabricanteProto proto = FabricanteProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer1);
            stub.deletar(proto);
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer2);
                stub.deletar(proto);
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    FabricanteServiceGrpc.FabricanteServiceBlockingStub stub = FabricanteServiceGrpc.newBlockingStub(channelCustomer3);
                    stub.deletar(proto);
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }
    }

    public List<Modelo> todosModelos() throws FalhaGrpcException {
        List<Modelo> modelos = new ArrayList<>();
        List<ModeloProto> modeloProtoList = new ArrayList<>();
        Empty empty = Empty.newBuilder().build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer1);
            modeloProtoList = stub.buscarTodos(empty).getModelosList();
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer2);
                modeloProtoList = stub.buscarTodos(empty).getModelosList();
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer3);
                    modeloProtoList = stub.buscarTodos(empty).getModelosList();
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        modeloProtoList.forEach(f-> modelos.add(ProtoConverterCustomer.protoToModel(f)));

        return modelos;
    }

    public Modelo salvarModelo(Modelo modelo) throws FalhaGrpcException {
        Modelo persistido = null;
        ModeloProto grpc = ProtoConverterCustomer.modelToProto(modelo);
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer1);
            persistido = ProtoConverterCustomer
                    .protoToModel(stub
                            .salvar(ProtoConverterCustomer.modelToProto(modelo))
                            .getModelo());
        }catch(Exception ex1){
            //TENTANDO COM CUSTOMER2
            try{
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer2);
                persistido = ProtoConverterCustomer
                        .protoToModel(stub
                                .salvar(ProtoConverterCustomer.modelToProto(modelo))
                                .getModelo());
            }catch(Exception ex2){
                //TENTANDO COM CUSTOMER3
                try{
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer3);
                    persistido = ProtoConverterCustomer
                            .protoToModel(stub
                                    .salvar(ProtoConverterCustomer.modelToProto(modelo))
                                    .getModelo());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        return persistido;
    }

    public Modelo buscarModelo(int id) throws FalhaGrpcException {
        Modelo modelo = null;
        ModeloProto proto = ModeloProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer1);
            modelo = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getModelo());
        }catch(Exception ex1){
            //TENTANDO COM CUSTOMER2
            try{
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer2);
                modelo = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getModelo());
            }catch(Exception ex2){
                //TENTANDO COM CUSTOMER3
                try{
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer3);
                    modelo = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getModelo());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return modelo;
    }

    public Modelo atualizarModelo(Modelo modelo) throws FalhaGrpcException {
        Modelo modeloAtualizado = null;
        ModeloProto proto = ProtoConverterCustomer.modelToProto(modelo);
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer1);
            modeloAtualizado = ProtoConverterCustomer
                    .protoToModel(stub
                            .atualizar(ProtoConverterCustomer.modelToProto(modelo)).getModelo());
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer2);
                modeloAtualizado = ProtoConverterCustomer
                        .protoToModel(stub
                                .atualizar(ProtoConverterCustomer.modelToProto(modelo)).getModelo());
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer3);
                    modeloAtualizado = ProtoConverterCustomer
                            .protoToModel(stub
                                    .atualizar(ProtoConverterCustomer.modelToProto(modelo)).getModelo());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        return modeloAtualizado;
    }

    public void deletarModelo(int id) throws FalhaGrpcException {
        ModeloProto proto = ModeloProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer1);
            stub.deletar(proto);
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer2);
                stub.deletar(proto);
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    ModeloServiceGrpc.ModeloServiceBlockingStub stub = ModeloServiceGrpc.newBlockingStub(channelCustomer3);
                    stub.deletar(proto);
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }
    }

    public List<Veiculo> todosVeiculos() throws FalhaGrpcException {
        List<Veiculo> veiculos = new ArrayList<>();
        List<VeiculoProto> veiculoProtoList = new ArrayList<>();
        Empty empty = Empty.newBuilder().build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            veiculoProtoList = VeiculoServiceGrpc.newBlockingStub(channelCustomer1).buscarTodos(empty)
                    .getVeiculosList();
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                veiculoProtoList = VeiculoServiceGrpc.newBlockingStub(channelCustomer2).buscarTodos(empty)
                        .getVeiculosList();
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    veiculoProtoList = VeiculoServiceGrpc.newBlockingStub(channelCustomer3).buscarTodos(empty)
                            .getVeiculosList();
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        veiculoProtoList.forEach(f-> veiculos.add(ProtoConverterCustomer.protoToModel(f)));

        return veiculos;
    }

    public Veiculo salvarVeiculo(Veiculo veiculo) throws FalhaGrpcException {
        Veiculo persistido = null;
        VeiculoProto grpc = ProtoConverterCustomer.modelToProto(veiculo);
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer1);
            persistido = ProtoConverterCustomer
                    .protoToModel(stub
                            .salvar(ProtoConverterCustomer.modelToProto(veiculo))
                            .getVeiculo());
        }catch(Exception ex1){
            //TENTANDO COM CUSTOMER2
            try{
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer2);
                persistido = ProtoConverterCustomer
                        .protoToModel(stub
                                .salvar(ProtoConverterCustomer.modelToProto(veiculo))
                                .getVeiculo());
            }catch(Exception ex2){
                //TENTANDO COM CUSTOMER3
                try{
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer3);
                    persistido = ProtoConverterCustomer
                            .protoToModel(stub
                                    .salvar(ProtoConverterCustomer.modelToProto(veiculo))
                                    .getVeiculo());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        return persistido;
    }

    public Veiculo buscarVeiculo(Long id) throws FalhaGrpcException {
        Veiculo veiculo = null;
        VeiculoProto proto = VeiculoProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer1);
            veiculo = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getVeiculo());
        }catch(Exception ex1){
            //TENTANDO COM CUSTOMER2
            try{
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer2);
                veiculo = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getVeiculo());
            }catch(Exception ex2){
                //TENTANDO COM CUSTOMER3
                try{
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer3);
                    veiculo = ProtoConverterCustomer.protoToModel(stub.buscar(proto).getVeiculo());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }

            }
        }

        return veiculo;
    }

    public Veiculo atualizarVeiculo(Veiculo veiculo) throws FalhaGrpcException {
        Veiculo veiculoAtualizado = null;
        VeiculoProto proto = ProtoConverterCustomer.modelToProto(veiculo);
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer1);
            veiculoAtualizado =  ProtoConverterCustomer
                    .protoToModel(stub
                            .atualizar(ProtoConverterCustomer.modelToProto(veiculo)).getVeiculo());
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer2);
                veiculoAtualizado =  ProtoConverterCustomer
                        .protoToModel(stub
                                .atualizar(ProtoConverterCustomer.modelToProto(veiculo)).getVeiculo());
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer3);
                    veiculoAtualizado =  ProtoConverterCustomer
                            .protoToModel(stub
                                    .atualizar(ProtoConverterCustomer.modelToProto(veiculo)).getVeiculo());
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }

        return veiculoAtualizado;
    }

    public void deletarVeiculo(Long id) throws FalhaGrpcException {
        VeiculoProto proto = VeiculoProto.newBuilder().setId(id).build();
        try{
            //TENTANDO COM CUSTOMER1
            ManagedChannel channelCustomer1 = ManagedChannelBuilder
                    .forAddress(hostsProperties.getCustomer1Host(), hostsProperties.getCustomer1Port())
                    .usePlaintext()
                    .build();
            VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer1);
            stub.deletar(proto);
        }catch(Exception ex1){
            try{
                //TENTANDO COM CUSTOMER2
                ManagedChannel channelCustomer2 = ManagedChannelBuilder
                        .forAddress(hostsProperties.getCustomer2Host(), hostsProperties.getCustomer2Port())
                        .usePlaintext()
                        .build();
                VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer2);
                stub.deletar(proto);
            }catch(Exception ex2){
                try{
                    //TENTANDO COM CUSTOMER3
                    ManagedChannel channelCustomer3 = ManagedChannelBuilder
                            .forAddress(hostsProperties.getCustomer3Host(), hostsProperties.getCustomer3Port())
                            .usePlaintext()
                            .build();
                    VeiculoServiceGrpc.VeiculoServiceBlockingStub stub = VeiculoServiceGrpc.newBlockingStub(channelCustomer3);
                    stub.deletar(proto);
                }catch(Exception ex3){
                    throw new FalhaGrpcException();
                }
            }
        }
    }


}
