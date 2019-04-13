package sd.oficina.store1;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;
import sd.oficina.store1.grpc.EstoqueImpl;
import sd.oficina.store1.grpc.NotaImpl;
import sd.oficina.store1.grpc.ServicoImpl;

import javax.persistence.Persistence;
import java.io.IOException;

class Store1Application {
    public static void main(String[] args) {

        // Inicializa serviço de EventRescue
        new Thread(Store1Application::startEventRescue).start();

        Server server = ServerBuilder.forPort(1111)
                .addService(new ServicoImpl())
                .addService(new EstoqueImpl())
                .addService(new NotaImpl())
                .build();

        System.out.println("Iniciando Server - Store 1");
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
                ServiceEnum.STORE,
                Persistence.createEntityManagerFactory("Projeto").createEntityManager()
        ).executeRescueEvents();
    }

}