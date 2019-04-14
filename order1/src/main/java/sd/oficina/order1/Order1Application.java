package sd.oficina.order1;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.order1.grpc.OrcamentoServiceImpl;
import sd.oficina.order1.grpc.OrderServiceImpl;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;

import javax.persistence.Persistence;
import java.io.IOException;

public class Order1Application {
    public static void main(String[] args) {

        // Inicializa serviço de EventRescue
        new Thread(Order1Application::startEventRescue).start();

        System.out.println("Iniciando Order1Application");
        Server server = ServerBuilder.forPort(4441)
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

    private static void startEventRescue() {

        new EventRescueManager(
                ServiceEnum.ORDER,
                Persistence.createEntityManagerFactory("order1-persistence").createEntityManager()
        ).executeRescueEvents();
    }

}
