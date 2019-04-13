package sd.oficina.person3;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.person3.grpc.CidadeService;
import sd.oficina.person3.grpc.ClienteService;
import sd.oficina.person3.grpc.EstadoService;
import sd.oficina.person3.grpc.FornecedorService;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;

import javax.persistence.Persistence;
import java.io.IOException;

import static sd.oficina.person3.config.StartEstadosECidades.start;

public class Person3Application {
    public static void main(String[] args) {

        // Inicializa serviço de EventRescue
        new Thread(Person3Application::startEventRescue).start();

        //Inicia as inserções de estados e cidades
        start();

        //
        System.out.println("Servidor Person 3 inicializado");
        //

        //Inicializando o server
        Server server = ServerBuilder
                .forPort(2222)
                .addService((BindableService) new CidadeService())
                .addService((BindableService) new ClienteService())
                .addService((BindableService) new EstadoService())
                .addService((BindableService) new FornecedorService())
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
                ServiceEnum.PERSON,
                Persistence.createEntityManagerFactory("persistencia").createEntityManager()
        ).executeRescueEvents();
    }

}
