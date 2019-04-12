package sd.oficina.order2;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.order2.grpc.OrcamentoService;
import sd.oficina.order2.grpc.OrdemServicoService;

import java.io.IOException;

public class Order2Application {

    public static void main(String[] args) {

        Server server = ServerBuilder.forPort(4444)
                .addService(new OrdemServicoService())
                .addService(new OrcamentoService())
                .build();

        System.out.println("Iniciando Server - Order 2");

        try {
            server.start();
            server.awaitTermination();

        } catch (IOException | InterruptedException ex) {

            System.out.println("Ocorreu um Problema ao Iniciar o Server - Order 2");
            ex.printStackTrace();
        } catch (Exception ex) {

            System.out.println("PROBLEMA GRAVE ao Iniciar o Server - Order 2");
            ex.printStackTrace();
        }

        System.out.println("Finalizado Server - Order 2");
    }

}
