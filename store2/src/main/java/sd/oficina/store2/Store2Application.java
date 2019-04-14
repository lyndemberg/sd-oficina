package sd.oficina.store2;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.store2.grpc.EstoqueService;
import sd.oficina.store2.grpc.ServicoService;

import javax.persistence.Persistence;
import java.io.IOException;

public class Store2Application {
    public static void main(String[] args) {

        // Inicializa serviço de EventRescue
        new Thread(Store2Application::startEventRescue).start();

        //
        System.out.println("Servidor Store 2 inicializado");
        //

        //Inicializando o server
        Server server = ServerBuilder
                .forPort(1112)
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

    private static void startEventRescue() {

        new EventRescueManager(
                ServiceEnum.STORE,
                Persistence.createEntityManagerFactory("persistencia").createEntityManager()
        ).executeRescueEvents();
    }

}
