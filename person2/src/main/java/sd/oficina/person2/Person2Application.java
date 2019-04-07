package sd.oficina.person2;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.person2.grpc.CidadeService;
import sd.oficina.person2.grpc.ClienteService;
import sd.oficina.person2.grpc.EstadoService;
import sd.oficina.person2.grpc.FornecedorService;

import java.io.IOException;

public class Person2Application {
    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(2222)
                .addService(new CidadeService())
                .addService(new ClienteService())
                .addService(new EstadoService())
                .addService(new FornecedorService())
                .build();
        //
        System.out.println("Iniciando Server - Person2");
        //
        server.start();
        server.awaitTermination();
    }
}
