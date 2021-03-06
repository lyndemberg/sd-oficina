package sd.oficina.person1;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.person1.grpc.CidadeService;
import sd.oficina.person1.grpc.ClienteService;
import sd.oficina.person1.grpc.EstadoService;
import sd.oficina.person1.grpc.FornecedorService;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;

import javax.persistence.Persistence;
import java.io.IOException;

import static sd.oficina.person1.config.StartEstadosECidades.start;


public class Person1Application {
    public static void main(String[] args) {

        // Inicializa serviço de EventRescue
        new Thread(Person1Application::startEventRescue).start();

        //Inicia as inserções de estados e cidades
        start();

        //
        System.out.println("Servidor Person 1 inicializado");
        //

        //Inicializando o server
        Server server = ServerBuilder
                .forPort(2221)
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
