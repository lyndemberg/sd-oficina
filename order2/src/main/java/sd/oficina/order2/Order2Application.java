package sd.oficina.order2;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.order2.grpc.OrcamentoService;
import sd.oficina.order2.grpc.OrdemServicoService;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;

import javax.persistence.Persistence;
import java.io.IOException;

public class Order2Application {

    public static void main(String[] args) {

        // Inicializa serviço de EventRescue
        new Thread(Order2Application::startEventRescue).start();

        Server server = ServerBuilder.forPort(4442)
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

    private static void startEventRescue() {

        new EventRescueManager(
                ServiceEnum.ORDER,
                Persistence.createEntityManagerFactory("order2-persistence").createEntityManager()
        ).executeRescueEvents();
    }

}
