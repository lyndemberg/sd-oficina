package sd.oficina.customer2;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.customer2.grpc.AnoModeloImpl;
import sd.oficina.customer2.grpc.FabricanteImpl;
import sd.oficina.customer2.grpc.ModeloImpl;
import sd.oficina.customer2.grpc.VeiculoImpl;

import java.io.IOException;

public class Customer2Application {
        public static void main(String[] args) {

            Server server = ServerBuilder.forPort(3333)
                    .addService(new FabricanteImpl())
                    .addService(new VeiculoImpl())
                    .addService(new AnoModeloImpl())
                    .addService(new ModeloImpl())
                    .build();

            System.out.println("Iniciando Server - Customer 2");
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
