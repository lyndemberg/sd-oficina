package sd.oficina.person2;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import sd.oficina.person2.grpc.CidadeService;
import sd.oficina.person2.grpc.ClienteService;
import sd.oficina.person2.grpc.EstadoService;
import sd.oficina.person2.grpc.FornecedorService;
import sd.oficina.shared.eventsrescue.EventRescueManager;
import sd.oficina.shared.model.ServiceEnum;

import javax.persistence.Persistence;
import java.io.IOException;

import static sd.oficina.person2.config.StartEstadosECidades.start;

public class Person2Application {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Inicializa serviço de EventRescue
        new Thread(Person2Application::startEventRescue).start();

        //Inicia as inserções de estados e cidades
        start();

        Server server = ServerBuilder.forPort(2222)
                .addService(new CidadeService())
                .addService(new ClienteService())
                .addService(new EstadoService())
                .addService(new FornecedorService())
                .build();
        //
        System.out.println("Iniciando Server - Person2");
        //
        server.start();
        server.awaitTermination();
    }

    private static void startEventRescue() {

        new EventRescueManager(
                ServiceEnum.PERSON,
                Persistence.createEntityManagerFactory("person2").createEntityManager()
        ).executeRescueEvents();
    }

}
