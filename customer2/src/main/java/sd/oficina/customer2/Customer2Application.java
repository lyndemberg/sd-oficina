package sd.oficina.customer2;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.customer2.grpc.AnoModeloImpl;
import sd.oficina.customer2.grpc.FabricanteImpl;
import sd.oficina.customer2.grpc.ModeloImpl;
import sd.oficina.customer2.grpc.VeiculoImpl;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;

import javax.persistence.Persistence;
import java.io.IOException;

public class Customer2Application {
    public static void main(String[] args) {

        // Inicializa serviço de EventRescue
        new Thread(Customer2Application::startEventRescue).start();

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

    private static void startEventRescue() {

        new EventRescueManager(
                ServiceEnum.CUSTOMER,
                Persistence.createEntityManagerFactory("Projeto").createEntityManager()
        ).executeRescueEvents();
    }

}
