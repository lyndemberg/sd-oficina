package sd.oficina.customer3;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.customer3.grpc.AnoModeloService;
import sd.oficina.customer3.grpc.FabricanteService;
import sd.oficina.customer3.grpc.ModeloService;
import sd.oficina.customer3.grpc.VeiculoService;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;

import javax.persistence.Persistence;
import java.io.IOException;

public class Customer3Application {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Inicializa serviço de EventRescue
        new Thread(Customer3Application::startEventRescue).start();

        Server server = ServerBuilder.forPort(3333)
                .addService(new FabricanteService())
                .addService(new VeiculoService())
                .addService(new AnoModeloService())
                .addService(new ModeloService())
                .build();
        //
        System.out.println("Iniciando Server - Customer 3");
        //
        server.start();
        server.awaitTermination();
    }

    private static void startEventRescue() {

        new EventRescueManager(
                ServiceEnum.CUSTOMER,
                Persistence.createEntityManagerFactory("customer3").createEntityManager()
        ).executeRescueEvents();
    }

}
