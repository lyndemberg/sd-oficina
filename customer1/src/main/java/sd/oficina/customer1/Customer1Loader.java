package sd.oficina.customer1;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.customer1.grpc.AnoModeloService;
import sd.oficina.customer1.grpc.FabricanteService;
import sd.oficina.customer1.grpc.ModeloService;
import sd.oficina.customer1.grpc.VeiculoService;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;

import javax.persistence.Persistence;
import java.io.IOException;

public class Customer1Loader {

    public static void main(String[] args) {

        // Inicializa serviço de EventRescue
        new Thread(Customer1Loader::startEventRescue).start();

        Server server = ServerBuilder.forPort(3331)
                .addService(new ModeloService())
                .addService(new VeiculoService())
                .addService(new AnoModeloService())
                .addService(new FabricanteService())
                .build();

        System.out.println("Iniciando Server - Customer 1");

        try {
            server.start();
            server.awaitTermination();

        } catch (IOException | InterruptedException ex) {

            System.out.println("Ocorreu um Problema ao Iniciar o Server - Customer 1");
            ex.printStackTrace();
        } catch (Exception ex) {

            System.out.println("PROBLEMA GRAVE ao Iniciar o Server - Customer 1");
            ex.printStackTrace();
        }

        System.out.println("Finalizado Server - Customer 1");

    }

    private static void startEventRescue() {

        new EventRescueManager(
                ServiceEnum.CUSTOMER,
                Persistence.createEntityManagerFactory("customer1-persistence").createEntityManager()
        ).executeRescueEvents();
    }

}
