package sd.oficina.store2;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.store2.grpc.EstoqueService;
import sd.oficina.store2.grpc.ServicoService;

import java.io.IOException;

public class Store2Application {
    public static void main(String[] args) {

        //
        System.out.println("Servidor Store 2 inicializado");
        //

        //Inicializando o server
        Server server = ServerBuilder
                .forPort(1111)
                .addService((BindableService) new EstoqueService())
                .addService((BindableService) new ServicoService())
                .build();

        try {
            server.start();
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
