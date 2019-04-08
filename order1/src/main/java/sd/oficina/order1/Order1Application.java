package sd.oficina.order1;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.order1.grpc.OrcamentoServiceImpl;
import sd.oficina.order1.grpc.OrderServiceImpl;

import java.io.IOException;

public class Order1Application {
    public static void main(String[] args) {
        System.out.println("Iniciando Order1Application");
        Server server = ServerBuilder.forPort(4444)
                .addService(new OrderServiceImpl())
                .addService(new OrcamentoServiceImpl())
                .build();

        try {
            server.start();
            server.awaitTermination();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
